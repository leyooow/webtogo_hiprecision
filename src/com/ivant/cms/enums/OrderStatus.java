package com.ivant.cms.enums;

/**
 * Status of current order.
 * 
 * @author Mark Kenneth M. Ra�osa
 *
 */
public enum OrderStatus {
	/** Order is newly purchased */
	NEW("New"),
	/** Order is currently on hold */
	PENDING("Pending"),
	/** Ordered items successfully purchased */
	COMPLETED("Completed"),
	/** Purchase of order is canceled */
	CANCELLED("Cancelled"),
	/** Error in processing */
	ERROR("Error"),
	/** Order rejected */
	REJECTED("Rejected"),
	
	IN_PROCESS("In Process") ,
	
	FOR_DELIVERY("For Delivery"),
	
	DELIVERY_IN_TRANSIT("Delivery In Transit"),
	
	DELIVERED("Delivered"),

	DISCONTINUED("Discontinued");
	
	
	//for HBC OrderStatus
	/*
	<option name="itemStatus"  value="" <c:if test="${status eq 'OK' }">selected</c:if>>OK</option>
	
	<option name="itemStatus"  value="IN PROCESS" <c:if test="${status eq 'IN PROCESS' }">selected</c:if>>IN PROCESS</option>
	 
	<option name="itemStatus"  value="FOR DELIVERY" <c:if test="${status eq 'FOR DELIVERY' }">selected</c:if>>FOR DELIVERY</option>
	
	<option name="itemStatus"  value="DELIVERY IN TRANSIT"  <c:if test="${status eq 'DELIVERY IN TRANSIT' }">selected</c:if>>DELIVERY IN TRANSIT</option>
	
	<option name="itemStatus"  value="DELIVERED"  <c:if test="${status eq 'DELIVERED' }">selected</c:if>>DELIVERED</option>
	
	IN_PROCESS
	
	
	*/
	
	private final transient String name;
	
	
	private OrderStatus(String name){
		
		this.name = name;
		
	}


	public String getName() {
		return name;
	}

	
	
	
}
