package com.tool.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.tool.common.DataBaseElement;
import com.tool.common.Property;

public class DataUtil {
	
	private static Connection getConn(DataBaseElement dataBase) throws SQLException {
	    String driver = Property.driver;
	    String url = "jdbc:mysql://"+dataBase.getIp()+":"+dataBase.getPort()+"/"+dataBase.getDb()+"?characterEncoding=utf8&useSSL=false&serverTimezone=UTC";
	    String username = dataBase.getUsername();
	    String password = dataBase.getPassword();
	    Connection conn = null;
	    try {
	        Class.forName(driver); //classLoader,加载对应驱动
	        conn = (Connection) DriverManager.getConnection(url, username, password);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw e;
	    }
	    return conn;
	}
	
	
	
	
	public static Map<String,String> selectTable(DataBaseElement dataBase) throws Exception {
		String sql = "select table_name ,table_comment "
				+ "from information_schema.tables "
				+ "where table_schema = ?";
	    Connection conn = getConn(dataBase);
	    ResultSet result = null;
	    PreparedStatement pstmt;
	    Map<String,String> map = new LinkedHashMap<>();
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setString(1, dataBase.getDb());
	        result = pstmt.executeQuery();
	        while(result.next()){         
	        	map.put(result.getString("table_name"), result.getString("table_comment"));
	      }
	        
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return map;
	}
	
	public static List<Schema> select(DataBaseElement dataBase,String tableName) throws Exception {
		String sql = "select column_name,IF(is_nullable='YES','是','否') is_nullable,data_type,"
				+ "character_maximum_length,column_type,column_comment "
				+ "from information_schema.columns "
				+ "where table_schema = ? and table_name = ?";
	    Connection conn = getConn(dataBase);
	    ResultSet result = null;
	    PreparedStatement pstmt;
	    List<Schema> list = new ArrayList<Schema>();
	    try {
	        pstmt = (PreparedStatement) conn.prepareStatement(sql);
	        pstmt.setString(1, dataBase.getDb());
	        pstmt.setString(2, tableName);
	        result = pstmt.executeQuery();
	        Schema schema = null;
	        while(result.next()){         
	        	schema = new Schema();
	        	schema.setColumnName(result.getString("column_name"));
	        	schema.setNullable(result.getString("is_nullable"));
	        	schema.setDataType(result.getString("data_type"));
	        	schema.setLength(result.getString("character_maximum_length"));
	        	schema.setColumnType(result.getString("column_type"));
	        	schema.setColumnComment(result.getString("column_comment"));
	        	list.add(schema);
	      }
	        
	        pstmt.close();
	        conn.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return list;
	}
	
	public static void main(String[] args) throws Exception {
		DataBaseElement dataBase = new DataBaseElement();
		dataBase.setIp(Property.ip).setPort(Property.port).setUsername(Property.username).setPassword(Property.password).setDb(Property.db);
		Connection conn = getConn(dataBase);
		System.out.println(conn);
		List rs = select(dataBase,Property.table);
		System.out.println(rs);
	}
	

}
