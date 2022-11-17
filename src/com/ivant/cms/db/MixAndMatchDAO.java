package com.ivant.cms.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ivant.cms.wrapper.PaintCategoryWrapper;
import com.ivant.cms.wrapper.PaintWrapper;
import com.ivant.constants.BoysenConstants;
import com.ivant.utils.JdbcConnector;

/**
 * 
 * @author Kent
 *
 */
public class MixAndMatchDAO 
		extends JdbcConnector 
{
	
	public MixAndMatchDAO(String host)
	{
		HOST = host;
	}
	
	public List<PaintWrapper> getAllPaint()
	{
		String QUERY = "SELECT * FROM paint WHERE VALID=1";
		List<PaintWrapper> list = new ArrayList<PaintWrapper>();
		
		Connection connection = null;
		try
		{
			connection = connect(BoysenConstants.DB_MIX_AND_MATCH, BoysenConstants.DB_USER, 
					BoysenConstants.DB_PASS, BoysenConstants.DB_PORT);
			
			PreparedStatement ps = connection.prepareStatement(QUERY);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				
				PaintWrapper paint = new PaintWrapper();
				paint.setId(rs.getLong("id"));
				paint.setCreatedOn(rs.getDate("created_on"));
				paint.setIsValid(rs.getBoolean("valid"));
				paint.setUpdatedOn(rs.getDate("updated_on"));
				paint.setBase(rs.getString("base"));
				paint.setColor(rs.getString("color"));
				paint.setColorHexValue(rs.getString("color_hex_value"));
				paint.setNote(rs.getString("note"));
				paint.setPaintCode(rs.getString("paint_code"));
				
				String category = rs.getString("paint_category");
				paint.setPaintCategory(category != null ? Long.parseLong(category) : null);
				
				String order = rs.getString("paint_order");
				paint.setPaintOrder(order != null ? Integer.parseInt(order) : null);
				
				list.add(paint);
			}	
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(connection != null)
			{
				close(connection);
			}
		}
		
		
		return list;
	}
	
	public List<PaintCategoryWrapper> getAllPaintCategory()
	{
		String QUERY = "SELECT * FROM paint_category WHERE VALID=1";
		
		Connection connection = null;
		List<PaintCategoryWrapper> list = new ArrayList<PaintCategoryWrapper>();
		
		try
		{
			connection = connect(BoysenConstants.DB_MIX_AND_MATCH, BoysenConstants.DB_USER, 
					BoysenConstants.DB_PASS, BoysenConstants.DB_PORT);
			
			PreparedStatement ps = connection.prepareStatement(QUERY);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next())
			{
				PaintCategoryWrapper category = new PaintCategoryWrapper();
				category.setId(rs.getLong("id"));
				category.setCreatedOn(rs.getDate("created_on"));
				category.setIsValid(rs.getBoolean("valid"));
				category.setUpdatedOn(rs.getDate("updated_on"));
				category.setCode(rs.getString("code"));
				category.setName(rs.getString("name"));
				category.setImageUrl(rs.getString("image_url"));
				category.setIsColorizer(rs.getBoolean("is_colorizer"));
				
				String parentId = rs.getString("parent_category");
				category.setParentCategory(parentId != null ? Long.parseLong(parentId) : null);
				category.setHasColorizerImageCategory(rs.getBoolean("has_colorizer_image_category"));
				category.setIsCustomPaint(rs.getBoolean("is_custom_paint"));
				
				list.add(category);
			}
		}
		catch (SQLException e)
		{
			
		}
		finally
		{
			if(connection != null)
			{
				close(connection);
			}
		}
		
		return list;
	}
	
}
