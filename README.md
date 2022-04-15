# <a id="top"></a>基于Hadoop的分布式云存储系统:
HDFS网盘，用户注册登录后可以将数据文件保存到云端的一种分布式云存储系统，可以在任何时候通过浏览器对文件上传、下载、编辑等操作。

## 版本
`version 1.0`

## 搭建环境
- [hadoop完全分布式教程](https://blog.csdn.net/qq_39785575/article/details/106300628)	
- ubuntu v20(可以至少三台虚拟机搭建完全分布式，也可以只用一台搭建伪分布式)
- hadoop v3.3.2
- Apache Tomcat v9.0.26
- MySql v8.0.28
- java v1.8.0


## 数据库格式
CREATE TABLE user(
    id BIGINT NOT NULL AUTO_INCREMENT,
    username VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,                  
    PRIMARY KEY(id)
)ENGINE = InnoDB  DEFAULT CHARSET = utf8;


## 涉及技术
- struts2框架
- HDFS-API的运用
- JSP+Servlet
- Bootstrap框架渲染
- 系统B/S结构
- JDBC编程
- MVC开发模式
	
## 实现功能
- 系统管理：用户登录，注册（未实现，可后台添加用户数据，并运行com.elon33.netdisc.HdfsDAO来创建用户初始文件夹），登出
- 文件管理：创建文件夹，上传文件，下载文件，在线编辑txt文件；
	
## 后续待完善功能
- 增加用户注册以及用户基本信息修改功能
- 用户权限修改功能
- 文件分享模块
- 将tomcat服务器部署到分布式结点上，并通过分布式锁保障文件一致性
- 采用新技术框架，springboot，mybatis等重构代码，并实现前后端分离

## 项目展示
![登陆界面](https://i.imgur.com/yv5EngR.png)

![主页1](https://i.imgur.com/VcjBCzJ.png)

![主页2](https://i.imgur.com/mAMWacg.png)

# 正式发行版本下载
[releases v1.0](https://github.com/yilong0722/HDFS-Netdisc/releases/tag/v1.0)

