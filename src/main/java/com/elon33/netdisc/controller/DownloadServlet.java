package com.elon33.netdisc.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import java.net.URLEncoder;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.mapred.JobConf;

import com.elon33.netdisc.model.HdfsDAO;

/**
 * Servlet implementation class DownloadServlet
 */
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		HttpSession session = request.getSession();
		// 将hdfs上的文件下载到tomcat
		String username = (String) session.getAttribute("username");
		String filePath = new String(request.getParameter("filePath").getBytes("ISO-8859-1"), "GB2312");
		String local = context.getInitParameter("file-download") + "/" + filePath.substring(filePath.lastIndexOf("/") + 1);
		System.out.println(filePath);
		System.out.println("======================");
		System.out.println(local);
		JobConf conf = HdfsDAO.config();
		HdfsDAO hdfs = new HdfsDAO(conf);
		hdfs.download(filePath, local);

		// 将tomcat服务器的文件下载到用户端
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition","attachment;filename=" + URLEncoder.encode(local.substring(local.lastIndexOf("/") + 1),"utf-8"));
		FileInputStream is = new FileInputStream(local);
		ServletOutputStream sos = response.getOutputStream();
		try{
			byte[] bytes = new byte[1024];
			int count = 0;
			while((count = is.read(bytes)) != -1){
				sos.write(bytes,0, count);
			}
		} finally {
			is.close();
			sos.close();
		}



		FileStatus[] list = hdfs.ls((String)session.getAttribute("currentPath"));
		request.setAttribute("list", list);
		request.getRequestDispatcher("index.jsp").forward(request,response);
	}

	/*** @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}
}
