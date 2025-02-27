package com.elon33.netdisc.model;

import java.sql.Connection;
import java.sql.DriverManager;
/**
 * 数据库访问模型
 * @author elon@elon33.com
 */
public class ConnDB {
	private Connection con = null;

	/**
	 * 返回数据库访问连接对象
	 * @return DB Connection
	 */
	public Connection getConn() {
		try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			// 得到连接
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hadoop?user=root&password=146477&useUnicode=true&characterEncoding=GB2312");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}
}
