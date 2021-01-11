package com.xiaomi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaomi.entity.Cart;
import com.xiaomi.entity.Good;
import com.xiaomi.service.CartService;
import com.xiaomi.service.GoodService;
import com.xiaomi.service.UsersService;

@WebServlet("/GoodServlet")
public class GoodServlet extends HttpServlet{

	public GoodServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取操作类型和输出流
		PrintWriter out = response.getWriter();
		String operate = request.getParameter("operate");
		GoodService service = new GoodService();
		
		if("xiaomi".equals(operate)) {
			List<Good> goodsList = service.duplicateRemovalGoodName(service.findAllGood());
			request.setAttribute("goodsList", goodsList);
			request.getRequestDispatcher("goods_list.jsp").forward(request, response);
		}else if("detail".equals(operate)) {
			String goodName = request.getParameter("goodName");
			List<Good> goodsList = null;
			try {
				goodsList = service.findGoodByGoodName(goodName);
			}catch (Exception e) {
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
			request.setAttribute("goodsList", goodsList);
			request.setAttribute("goodtList", service.getGoodtList(goodsList));
			request.setAttribute("goodcList", service.getGoodcList(goodsList));
			request.getRequestDispatcher("goods_details.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取操作类型和输出流
		PrintWriter out = response.getWriter();
		String operate = request.getParameter("operate");
		GoodService service = new GoodService();
		CartService cartService = new CartService();
		
		if("search".equals(operate)) {
			String good_name = request.getParameter("good_name");
			List<Good> goodsList = service.duplicateRemovalGoodName(service.selectByGoodNameVagueLike(good_name));
			request.setAttribute("searchgoods", service.duplicateRemovalGoodName(goodsList));
			request.getRequestDispatcher("searchlist.jsp").forward(request, response);
		}else if("buy".equals(operate)) {
			String goodName = request.getParameter("good_name");
			String goodType = request.getParameter("type");
			String goodColor = request.getParameter("color");
			Good good = service.findGoodByNameTypeColor(new Good(goodName, goodType, goodColor));
			// 添加购物车
			cartService.insertCart(request,good);
			List<Cart> cartList = cartService.findCartByUid(request);
			request.setAttribute("cartlist", cartList);
			request.getRequestDispatcher("success_add_cart.jsp").forward(request, response);
		}
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.service(request, response);
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init() throws ServletException {

	}
	

}
