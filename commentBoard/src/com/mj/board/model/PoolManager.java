package com.mj.board.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class PoolManager {
	private static PoolManager instance;
	DataSource ds;
	
	private PoolManager() {
		try {
			InitialContext context=new InitialContext();
			ds=(DataSource)context.lookup("java:comp/env/jdbc/oracle");  //connection pool 생성
		} catch (NamingException e) {
			e.printStackTrace();
		}
		
	}
	
	public static PoolManager getInstance() {
		if(instance==null) {
			instance=new PoolManager();
		}
		return instance;
	}
	
	//풀로부터 Connection 하나 얻기
	public Connection getConnection() {
		Connection con=null;
		
		try {
			con=ds.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	//풀로 돌려보내는 메서드
	public void freeConnection(Connection con) {
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	// 풀로 돌려보내는 메서드
	public void freeConnection(Connection con, PreparedStatement pstmt) {
		try {
			pstmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
