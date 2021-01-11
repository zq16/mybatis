package com.xiaomi.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.xiaomi.entity.Users;


public class LoginFilter implements Filter{
	
	String[] ignoreUrl;

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest)request;
		HttpSession session = req.getSession();
		
		String url = req.getRequestURI();
		String rootUrl = session.getServletContext().getContextPath() + "/";
		
		if(url.equals(rootUrl)) {
			chain.doFilter(request, response);
			return;
		}
		
		for(String u:ignoreUrl) {
			if(url.endsWith(u)) {
				chain.doFilter(request, response);
				return;
			}
		}
		
		if(session.getAttribute("loggedUser") == null) {
			//设置调试用户
			Users user = new Users();
			user.setUid(1);
			user.setUsername("调试用户");
			session.setAttribute("loggedUser", user);
			return;
			//设置调试用户
//			 ((HttpServletResponse)response).sendRedirect("login.jsp");
		}else {
			chain.doFilter(request, response);
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		this.ignoreUrl = filterConfig.getInitParameter("ignoreUrl").split(",");
	}

}
