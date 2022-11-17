package com.ivant.cms.action.admin.dwr;

import com.ivant.cms.entity.Wishlist;

/**
 * Wishlist reverse ajax class.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public class DWRWishlistAction extends AbstractDWRAction {
	
	/**
	 * Returns the wishlist items based on the specified wishlist ID parameter.
	 * 
	 * @param wishListID
	 * 			- id of wishlist, must not be null
	 * 
	 * @return - the wishlist items based on the specified wishlist ID parameter
	 */
	public String getWishlist(String wishListID){
		//get wishlist id
		Long wishlistID = Long.parseLong(wishListID);
		//load wishlist
		Wishlist wishlist = wishlistDelegate.find(wishlistID);		
		//load all items in wishlist
		
		
		//System.out.println("wishlist : " + wishlist);
		
		//generate html string containing list
		StringBuilder htmlString = new StringBuilder();
		htmlString.append("<h5>Wishlist ID : ");
		htmlString.append(wishListID);
		htmlString.append("</h5>");
		htmlString.append("<h5>Member Name : ");
		htmlString.append(wishlist.getMember().getLastname());
		htmlString.append(", ");
		htmlString.append(wishlist.getMember().getFirstname());
		htmlString.append("</h5>");
		htmlString.append("<table><tr>");
		htmlString.append("  <td>");
		htmlString.append("<strong>Item Name</strong>");
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		htmlString.append("<strong>Description</strong>");
		htmlString.append("   </td>");		
		htmlString.append("</tr>");
		
		
		htmlString.append("<tr>");
		htmlString.append("  <td>");
		htmlString.append(wishlist.getItem().getName());
		htmlString.append("   </td>");
		htmlString.append("  <td>");
		htmlString.append(wishlist.getItem().getDescription());
		htmlString.append("   </td>");			
		htmlString.append("</tr>");
		
		
		
		//System.out.println(htmlString.toString());
		//return generated html string
		return htmlString.toString();
	}
}
