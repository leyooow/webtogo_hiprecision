package com.ivant.cms.wrapper;

import com.ivant.cms.entity.baseobjects.BaseObject;

public class PaintWrapper extends BaseObject 
{
	private String base;
	private String color;
	private String colorHexValue;
	private String note;
	private String paintCode;
	private Long paintCategory;
	private Integer paintOrder;
	
	public static final String [] FIELDS_PAINT = {
		"id",
		"created_on",
		"valid",
		"updated_on",
		"base",
		"color",
		"color_hex_value",
		"note",
		"paint_code",
		"paint_category",
		"paint_order"
	};
	
	public PaintWrapper()
	{
		
	}

	public String getBase() {
		return base;
	}

	public void setBase(String base) {
		this.base = base;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColorHexValue() {
		return colorHexValue;
	}

	public void setColorHexValue(String colorHexValue) {
		this.colorHexValue = colorHexValue;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPaintCode() {
		return paintCode;
	}

	public void setPaintCode(String paintCode) {
		this.paintCode = paintCode;
	}

	public Long getPaintCategory() {
		return paintCategory;
	}

	public void setPaintCategory(Long paintCategory) {
		this.paintCategory = paintCategory;
	}

	public Integer getPaintOrder() {
		return paintOrder;
	}

	public void setPaintOrder(Integer paintOrder) {
		this.paintOrder = paintOrder;
	}
	
}
