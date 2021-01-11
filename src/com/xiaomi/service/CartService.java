package com.xiaomi.service;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;

import com.xiaomi.entity.Cart;
import com.xiaomi.entity.CartExample;
import com.xiaomi.entity.CartExample.Criteria;
import com.xiaomi.entity.Good;
import com.xiaomi.entity.Users;
import com.xiaomi.mapper.CartMapper;
import com.xiaomi.util.DBUtil;

public class CartService {

	public boolean insertCart(HttpServletRequest request, Good good) {
		// 获取用户信息和商品信息
		Integer uid = ((Users) request.getSession().getAttribute("loggedUser")).getUid();
		Integer goodId = good.getGoodId();
		Integer goodNum = 1;
		Float singlePrice = good.getGoodPrice();
		Cart cart = new Cart(uid, goodId, goodNum, 0, singlePrice);
		// 获取当前购物车信息
		cart.setGoodNum(getCartGoodNum(cart));
		cart.setPrice(cart.getGoodNum() * singlePrice);
		Cart wholeCart = findCartByUidGoodIdStatus(cart);
		cart.setPreId(wholeCart != null ? wholeCart.getPreId() : 0);
		SqlSession session = DBUtil.getSession();
		CartMapper mapper = session.getMapper(CartMapper.class);
		if(cart.getGoodNum() > 1) {
			//购物车已有的商品修改数量
			mapper.updateByPrimaryKey(cart);
		}else {
			//购物车没有的商品添加购物车
			mapper.insert(cart);
		}
		session.commit();
		session.close();
		return false;
	}

	public int getCartGoodNum(Cart cart) {
		cart = findCartByUidGoodIdStatus(cart);
		if (cart != null) {
			return cart.getGoodNum() + 1;
		} else {
			return 1;
		}
	}

	public Cart findCartByUidGoodIdStatus(Cart cart) {
		SqlSession session = DBUtil.getSession();
		CartMapper mapper = session.getMapper(CartMapper.class);
		CartExample example = new CartExample();
		Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(cart.getUid());
		criteria.andGoodIdEqualTo(cart.getGoodId());
		criteria.andStatusEqualTo(cart.getStatus());
		List<Cart> cartList = mapper.selectByExample(example);
		session.close();
		return cartList.size() > 0 ? cartList.get(0) : null;
	}
	
	public List<Cart> findCartByUid(HttpServletRequest request) {
		Integer uid = ((Users) request.getSession().getAttribute("loggedUser")).getUid();
		SqlSession session = DBUtil.getSession();
		CartMapper mapper = session.getMapper(CartMapper.class);
		CartExample example = new CartExample();
		Criteria criteria = example.createCriteria();
		criteria.andUidEqualTo(uid);
		List<Cart> cartList = mapper.selectByExample(example);
		if(cartList.size() > 0) 
			cartList.stream().forEach(cart->cart.setG(new GoodService().findGoodByGoodId(cart.getGoodId())));
		session.close();
		return cartList.size() > 0 ? cartList : null;
	}
	
	public Cart findCartByPreId(int preId) {
		SqlSession session = DBUtil.getSession();
		CartMapper mapper = session.getMapper(CartMapper.class);
		Cart cart = mapper.selectByPrimaryKey(preId);
		session.close();
		return cart;
	}
	
	public int deleteCartByUid(int preId) {
		SqlSession session = DBUtil.getSession();
		CartMapper mapper = session.getMapper(CartMapper.class);
		int count = mapper.deleteByPrimaryKey(preId);
		session.commit();
		session.close();
		return count;
	}
	
	public boolean changeNumber(int goodNum,int cartId) {
		Cart cart = findCartByPreId(cartId);
		Good good = new GoodService().findGoodByGoodId(cart.getGoodId());
		int stock = good.getGoodCount();
		if(goodNum > stock || goodNum <= 0) 
			return false;
		cart.setGoodNum(goodNum);
		cart.setPrice(good.getGoodPrice() * cart.getGoodNum());
		updateCart(cart);
		return true;
	}
	
	public boolean updateCart(Cart cart) {
		SqlSession session = DBUtil.getSession();
		CartMapper mapper = session.getMapper(CartMapper.class);
		int count = mapper.updateByPrimaryKeySelective(cart);
		session.commit();
		session.close();
		return count > 0 ? true:false;
	}
	
	public boolean submitMorder(int uid,List<Integer> cardIdList) {
		StringBuilder cartOrder = new StringBuilder();
		for(int cartId:cardIdList) {
			Cart cart = new Cart();
			cart.setPreId(cartId);
			cart.setStatus(1);
			updateCart(cart);
			cartOrder.append(cartId+"#");
		}
		System.out.println(cartOrder);
		return false;
	}

}
