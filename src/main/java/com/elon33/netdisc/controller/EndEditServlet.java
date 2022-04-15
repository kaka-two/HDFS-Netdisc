package com.elon33.netdisc.controller;

import com.elon33.netdisc.model.HdfsDAO;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.mapred.JobConf;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class EndEditServlet extends HttpServlet {

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        this.doPost(request,response);


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {

        HttpSession session = request.getSession();
        String local = (String) session.getAttribute("localPath");
        String editedFile = new String(request.getParameter("editedFile").getBytes("iso8859-1"), "utf-8");
        System.out.println("服务器文件地址： " + local);
        System.out.println("修改后文件内容： " + editedFile);
        System.out.println("========================");
        // 将页面内容写入tomcat服务器对应文件
        System.out.println("开始写入tomcat");
        try(OutputStream os = new FileOutputStream(local)){
            os.write(editedFile.getBytes("utf-8"));
            System.out.println("写入本地的文件内容" + editedFile);
        }
        System.out.println("成功写入tomcat文件");

        System.out.println("========================");

        System.out.println("开始提交");
        // 删除tomcat服务器对应文件，并重新提交
        String filePath = (String) session.getAttribute("filePath");
        System.out.println("hdfs路径： " + filePath);
        JobConf conf = HdfsDAO.config();
        HdfsDAO hdfs = new HdfsDAO(conf);
        //删除crc文件，使得可以上传。
        File localCrc = new File(local.substring(0, local.lastIndexOf("/") + 1) + "." + local.substring(local.lastIndexOf("/") + 1) + ".crc");
        localCrc.delete();

        hdfs.rmr(filePath);
        hdfs.copyFile(local, filePath);
//        if(filePath.lastIndexOf("_v") > 0){
//            int ver = Integer.parseInt(filePath.substring(filePath.lastIndexOf("_v") + 2, filePath.lastIndexOf(".txt"))) + 1;
//            hdfs.copyFile( local, filePath.substring(0, filePath.lastIndexOf("_v") + 2) + ver + ".txt" );
//        }
//        else{
//            hdfs.copyFile( local, filePath.substring(0, filePath.lastIndexOf(".txt")) + "_v1.txt" );
//        }

        System.out.println("upload file to hadoop hdfs success!");
//        File localFile = new File(local);
//        localFile.delete();
//        System.out.println("成功清空缓存");



        FileStatus[] list = hdfs.ls((String)session.getAttribute("currentPath"));
        request.setAttribute("list", list);
        request.getRequestDispatcher("index.jsp").forward(request,response);
    }

}
