package com.xiaomi.servlet;

import java.io.File;

import com.jspsmart.upload.Files;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/TestUpload2")
public class TestUpload2 extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext pageContext = request.getServletContext();
		File tempDir = new File(pageContext.getRealPath("/temp"));
		File uploadDir = new File("D:\\XiaoMiStorage");
		if(!tempDir.exists()) {
			tempDir.mkdirs();
		}
		if(!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		SmartUpload su = new SmartUpload();
		su.setMaxFileSize(10000000);
		PageContext myPageContext = JspFactory.getDefaultFactory().getPageContext(this, request, response, null, false, 8192, true);
		try {
			su.initialize(myPageContext);
			su.upload();
			su.save(uploadDir.getPath());
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		request.getRequestDispatcher("testFileUpload.jsp").forward(request, response);
	}

	@Override
	protected void service(HttpServletRequest arg0, HttpServletResponse arg1) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(arg0, arg1);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		super.destroy();
	}

	@Override
	public void init() throws ServletException {
		// TODO Auto-generated method stub
		super.init();
	}
	
}
