package com.xiaomi.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.xiaomi.entity.Users;
import com.xiaomi.service.UsersService;
import com.xiaomi.util.CommonUtil;

@WebServlet("/UsersServlet")
public class UsersServlet extends HttpServlet{
	
	public UsersServlet() {
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取操作类型和输出流
		PrintWriter out = response.getWriter();
		String operate = request.getParameter("operate");
		if("logout".equals(operate)) {
			// 销毁会话
			request.getSession().invalidate();
			out.append("<script>");
			out.append("alert('成功退出登录！');");
			out.append("window.location.href = 'index.jsp';");
			out.append("</script>");
		}else if("selfinfo".equals(operate)) {
			request.getRequestDispatcher("self_info.jsp").forward(request, response);
		}else if("edit".equals(operate)) {
			request.getRequestDispatcher("edituser.jsp").forward(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 获取操作类型、输出流、服务对象
		PrintWriter out = response.getWriter();
		UsersService service = new UsersService();
		String operate = request.getParameter("operate");
		//获取用户信息
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String phonenumber = request.getParameter("phonenumber");
		String address = request.getParameter("address");
		String hobby = request.getParameter("hobby");
		String sign = request.getParameter("sign");
		Users user = new Users(username,password,phonenumber,address,hobby,sign);
		// 保存提交的表单 
		request.setAttribute("users", user);
		if("register".equals(operate)) {	//注册功能
			String image = request.getParameter("image");
			//验证码
			if(!service.validCode(image, request)) {
				request.setAttribute("errorMsg", "imageError");
				request.getRequestDispatcher("register.jsp").forward(request, response);
				return;
			}
			//用户名是否存在
			if(service.hasUser(username)) {
				request.setAttribute("errorMsg", "usernameError");
				request.getRequestDispatcher("register.jsp").forward(request, response);
				return;
			}
			
			service.register(user);
			//提示信息+自动跳转
			out.append("<script>");
			out.append("alert('注册成功！三秒后自动跳转登录页面');");
			out.append("setTimeout(function() {window.location.href = 'login.jsp';}, 3000);");
			out.append("</script>");
		}else if("login".equals(operate)) {	//登录功能
			String image = request.getParameter("image");
			//验证码
			if(!service.validCode(image, request)) {
				request.setAttribute("errorMsg", "imageError");
				request.getRequestDispatcher("login.jsp").forward(request, response);
				return;
			}
			//登录+提示信息+自动跳转
			if(service.login(user)) {
				HttpSession session = request.getSession();
				//设置Session对象
				session.setAttribute("loggedUser", service.getLoginUsers(user));
				out.append("<script>");
				out.append("alert('登录成功！三秒后自动跳转首页');");
				out.append("setTimeout(function() {window.location.href = 'index.jsp';}, 3000);");
				out.append("</script>");
			}else {
				request.setAttribute("errorMsg", "pwdError");
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
				
		}else if("editupdate".equals(operate)) {
			user.setUid(Integer.parseInt(request.getParameter("uid")));
			service.updateUsers(user);
			//更新Session对象
			HttpSession session = request.getSession();
			session.setAttribute("loggedUser", service.getLoginUsers(user));
			out.append("<script>");
			out.append("alert('修改成功！');");
			out.append("window.location.href = 'self_info.jsp';");
			out.append("</script>");
		}
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		super.service(arg0, arg1);
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}
	
}
