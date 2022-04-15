<%@ include file="head.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.io.FileInputStream"%>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.io.BufferedReader"%>
<%@ page import="javax.servlet.ServletContext"%>
<%@ page import="javax.servlet.http.HttpSession"%>
<body style="text-align:center;margin-bottom:100px;">
    <div  style="text-align:left;margin:0px auto;margin-top:50px; width:1200px;height:900px;">
    <form action="EndEditServlet" method="post" style="margin-top:80px;margin-left:100px;" >
        <textarea id = "editedFile" rows="40" cols="70" width="1000px" style="text-align: left; width:1000px;height:800px; line-height: 1.5; font: 24px 宋体;border: 0" name="editedFile"><%
                String local = (String) session.getAttribute("localPath");
                int n = 0;
                String line;
                try(FileInputStream is = new FileInputStream(local)){
                    InputStreamReader reader = new InputStreamReader(is,"UTF-8");
                    BufferedReader br = new BufferedReader(reader);
                    while((line = br.readLine()) != null){
                        out.print(line);
                        out.print("\n");
                        System.out.println("原文件内容： "+ line);
                    }
                    br.close();
                    reader.close();
                }
                System.out.println("成功获取文件");
            %></textarea>
         <br><br>
        <button style="margin-right:100px; float: right; " type="submit" class="btn">提交</button>
    </form>
    </div>
</body>

