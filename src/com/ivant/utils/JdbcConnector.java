package com.ivant.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 
 * @author Kent
 *
 */
public class JdbcConnector 
{
	protected String HOST;
	
	protected final Connection connect(String dbName, String username, String password, int port)
	{
		Connection conn = null;
		
		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(getJdbcUrl(dbName, port), username, password);
			System.out.println("DB Connection Successful!");
		}
		catch (SQLException e)
		{
			System.out.println("***** details *****");
			System.out.println(HOST + " / " + username + " / " + password + " / " + port);
			System.out.println(getJdbcUrl(dbName, port));
			
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
		return conn;
	}

	protected final void close(Connection conn) 
	{
	    if(conn != null)
	    {
	    	try {
	    		conn.close();
	      } catch (SQLException e) {
	        System.out.println("SQLException: " + e);
	        e.printStackTrace();
	      }
	    }
	}
	
	private String getJdbcUrl(String dbName, int port)
	{
		StringBuffer sb = new StringBuffer("jdbc:mysql://")
			.append(HOST)
			.append(":")
			.append(port)
			.append("/")
			.append(dbName);
		
		return sb.toString();
	}
	
}
