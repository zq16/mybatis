package com.xiaomi.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

@WebServlet("/TestUpload1")
public class TestUpload1 extends HttpServlet{

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
		DiskFileItemFactory diskFactory = new DiskFileItemFactory();
		// 设置缓冲区的临界值
		diskFactory.setSizeThreshold(1024);
		// 设置临时文件位置
		diskFactory.setRepository(tempDir);
		ServletFileUpload upload = new ServletFileUpload(diskFactory);
		upload.setSizeMax(10000000);
		List<FileItem> fileList = null;
		try {
			fileList = upload.parseRequest(request);
			request.setCharacterEncoding("gb2312");
			
			upload.setHeaderEncoding("utf-8");
			for(FileItem fi:fileList) {
				if(fi.isFormField()) {
					System.out.println(fi.getString());
				}else {
					System.out.println(fi.getName());
					System.out.println(fi.getSize());
					File upfile = new File(uploadDir.getPath(),fi.getName());
					fi.write(upfile);
				}
				
			}
		} catch (Exception e) {
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
