package com.elon33.netdisc.controller;

import com.elon33.netdisc.model.HdfsDAO;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.mapred.JobConf;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class EditFileServlet extends HttpServlet {

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        ServletContext context = getServletContext();
        HttpSession session = request.getSession();
        // 将hdfs上的文件下载到tomcat
        String username = (String) session.getAttribute("username");
        String filePath = new String(request.getParameter("filePath").getBytes("ISO-8859-1"), "GB2312");
        session.setAttribute("filePath", filePath);
        String local = context.getInitParameter("file-edit") + "/" + filePath.substring(filePath.lastIndexOf("/") + 1);
        System.out.println(filePath);
        System.out.println("======================");
        System.out.println(local);
        JobConf conf = HdfsDAO.config();
        HdfsDAO hdfs = new HdfsDAO(conf);
        hdfs.download(filePath, local);
        session.setAttribute("localPath", local);

        FileStatus[] list = hdfs.ls((String)session.getAttribute("currentPath"));
        request.setAttribute("list", list);
        request.getRequestDispatcher("Edit.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        this.doGet(request, response);
    }

}
