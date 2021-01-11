package com.xiaomi.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaomi.entity.Cart;
import com.xiaomi.entity.Users;
import com.xiaomi.service.CartService;
import com.xiaomi.util.CommonUtil;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet{

	public CartServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CartService service = null;
		PrintWriter out = null;
		String operate = null;
		
		try {
			service = new CartService();
			out = response.getWriter();
			operate = request.getParameter("operate");
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
		if("deleteCart".equals(operate)) {
			String id = request.getParameter("id");
			service.deleteCartByUid(Integer.parseInt(id));
		}
		
		request.setAttribute("cartlist", service.findCartByUid(request));
		request.getRequestDispatcher("cartlist.jsp").forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CartService service = new CartService();
		PrintWriter out = response.getWriter();
		String operate = request.getParameter("operate");
		
		if("change_number".equals(operate)) {
			int goodNum = Integer.parseInt(request.getParameter("good_num"));
			int cartId = Integer.parseInt(request.getParameter("cart_id"));
			service.changeNumber(goodNum, cartId);
			Cart cart = service.findCartByPreId(cartId);
			try {
				out.append(CommonUtil.convertBeanToJSONObject(cart).toString());
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}else if("jiesuan".equals(operate)) {
			String[] valList = request.getParameterValues("check");
			List<Integer> cardIdList = Stream.of(valList).map(val->Integer.parseInt(val)).collect(Collectors.toList());
			Integer uid = ((Users) request.getSession().getAttribute("loggedUser")).getUid();
			service.submitMorder(uid, cardIdList);
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
