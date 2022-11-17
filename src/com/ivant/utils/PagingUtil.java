package com.ivant.utils;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/** Utility to create pagination
 * 
 * @author Allan
 * @version 1.0
 */
public class PagingUtil {
	
	public static final int DEFAULT_ITEMS_PER_PAGE = 8;
	public static final int DEFAULT_ITEMS_PER_PAGE_CMS = 10;
	public static final int DEFAULT_PAGE_NUMBERS_TO_DISPLAY = 10;
	
	private List<Integer> pages;
	private int totalPages;
	private int totalItems;
	private int itemsPerPage;
	private int currentPageNo;
	private int pageNumbersToDisplay;
	private int halfPageNumbersToDisplay;
	
	private boolean hasPrev;
	private boolean hasNext;
	
	private boolean hasLeftDots;
	private boolean hasRightDots;
	
	private boolean showFirst;
	private boolean showLast;
	
	private int startingItemNo;
	
	/** Create pagination with default current page is 1 and DEFAULT_PAGE_NUMBERS_TO_DISPLAY, DEFAULT_ITEMS_PER_PAGE attribute
	 * @param totalItems
	 */
	public PagingUtil(){
		pages = new LinkedList<Integer>();		
	}
	
	public PagingUtil(int totalItems) {
		this(totalItems, 1);
	}
	
	/** Create pagination with DEFAULT_PAGE_NUMBERS_TO_DISPLAY, DEFAULT_ITEMS_PER_PAGE attribute
	 * @param totalItems
	 * @param currentPageNo
	 */
	public PagingUtil(int totalItems, int currentPageNo) {
		this(totalItems, DEFAULT_ITEMS_PER_PAGE, currentPageNo, DEFAULT_PAGE_NUMBERS_TO_DISPLAY);
	}
	
	/** Create pagination with DEFAULT_PAGE_NUMBERS_TO_DISPLAY attribute
	 * @param totalItems
	 * @param itemsPerPage
	 * @param currentPageNo
	 */
	public PagingUtil(int totalItems, int itemsPerPage, int currentPageNo) {
		this(totalItems, itemsPerPage, currentPageNo, DEFAULT_PAGE_NUMBERS_TO_DISPLAY);
	}
	
	/** Create pagination with full control of the attributes
	 * @param totalItems total items in the list
	 * @param itemsPerPage items to be displayed per page
	 * @param currentPageNo current selected page number 
	 * @param pageNumbersToDisplay numbers of pages to display etc. [1 2 3 4 5] has 5 pageNumbersToDisplay
	 */
	public PagingUtil(int totalItems, int itemsPerPage, int currentPageNo, int pageNumbersToDisplay ) {
		this.totalItems = totalItems;
		this.itemsPerPage = itemsPerPage;
		this.currentPageNo = currentPageNo;
		this.pageNumbersToDisplay = pageNumbersToDisplay;
		halfPageNumbersToDisplay = pageNumbersToDisplay / 2;
		if(pageNumbersToDisplay % 2 != 0){
			halfPageNumbersToDisplay++;
		}
		pages = new LinkedList<Integer>();
		this.process();
	}
	
	/**
	 * @return currently selected page number
	 */
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	
	/**
	 * @return items per page
	 */
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	
	/**
	 * @return page numbers to display
	 */
	public int getPageNumbersToDisplay() {
		return pageNumbersToDisplay;
	}

	/**
	 * @return list of pages to be displayed
	 */
	public List<Integer> getPages() {
		return pages;
	}

	/**
	 * @return overall total items in the list
	 */
	public int getTotalItems() {
		return totalItems;
	}

	/**
	 * @return calculated total pages
	 */
	public int getTotalPages() {
		return totalPages;
	}

	/** Determine if there are lower pages exist but not displayed.
	 * @return true if left dots (...) is to be displayed; otherwise false
	 */
	public boolean isHasLeftDots() {
		return hasLeftDots;
	}


	/**
	 * @return true if it has next page; otherwise false
	 */
	public boolean isHasNext() {
		return hasNext;
	}

	/**
	 * @return true if it has previous page; otherwise false
	 */
	public boolean isHasPrev() {
		return hasPrev;
	}

	/** Determine if there are higher pages exist but not displayed
	 * @return true if it has right dots (...); otherwise false
	 */
	public boolean isHasRightDots() {
		return hasRightDots;
	}

	public void doPaginate(){
		process();
	}
	/**
	 *  Calculation of pagination
	 */
	private void process() {
		if(totalItems == 0 || itemsPerPage == 0) {
			return;
		}
		
		if(currentPageNo == 0) {
			currentPageNo++;
		}
		
		totalPages = totalItems / itemsPerPage;
		if( (totalItems % itemsPerPage) > 0 ){
			totalPages++;
		}
		
		if (currentPageNo > 1)
			hasPrev = true;
		
		if(currentPageNo < totalPages )
			hasNext = true;
		
		if( Math.min(totalPages, currentPageNo) < pageNumbersToDisplay ||
				totalPages == pageNumbersToDisplay) {
			for(int i = 1; i <= Math.min(totalPages, pageNumbersToDisplay); i++) {
				pages.add(i);
			}
//			if(pages.size() > 0 && pages.get(0) > pageNumbersToDisplay){
//				if(pages.size() > 0 && pages.get(0) > pageNumbersToDisplay){
//				showFirst = true;
//			}
		}
		else {
			int pageCounterUp = 0;
			for(int i = 0; i < halfPageNumbersToDisplay; i++  ){
				pages.add(i + currentPageNo);
				pageCounterUp++;
				if(currentPageNo + pageCounterUp > totalPages)
					break;
			}
			int pageCounterDown = pageNumbersToDisplay - pageCounterUp;
			for (int i = 1; i <= pageCounterDown; i++) {
				pages.add(currentPageNo - i);
			}
			
			Collections.sort(pages);
			
			
			/*int startingPage = 1;
			int loop = currentPageNo / (pageNumbersToDisplay);
			
			if(currentPageNo % pageNumbersToDisplay == 0)
				loop--;
			
			for(int i = 0; i < loop; i++) {
				startingPage+=pageNumbersToDisplay;
			}
			int excess = totalPages - startingPage;
			for(int i= 0, j = startingPage; i <= excess && i < pageNumbersToDisplay ; i++) {
				pages.add(j++);
			}*/
			
		}
		
		if(pages.size() > 0){
			int first = pages.get(0);
			if(first > 1){
				hasLeftDots = true;
				//if(showFirst != true && first > halfPageNumbersToDisplay ){
					showFirst = true;
//				}
			}
			int last = pages.get( pages.size() - 1);
			if(last < totalPages ){
				hasRightDots = true;
//				if(last + halfPageNumbersToDisplay < totalPages){
					showLast = true;
//				}
			}
		}
		
		startingItemNo = (currentPageNo - 1) * itemsPerPage;
		
		return;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public void setHasLeftDots(boolean hasLeftDots) {
		this.hasLeftDots = hasLeftDots;
	}
	
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public void setHasPrev(boolean hasPrev) {
		this.hasPrev = hasPrev;
	}

	public void setHasRightDots(boolean hasRightDots) {
		this.hasRightDots = hasRightDots;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public void setPageNumbersToDisplay(int pageNumbersToDisplay) {
		this.pageNumbersToDisplay = pageNumbersToDisplay;
		halfPageNumbersToDisplay = pageNumbersToDisplay / 2;
		if(pageNumbersToDisplay % 2 != 0){
			halfPageNumbersToDisplay++;
		}
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	/**
	 * used in testing
	 * @param args
	 */
	public static void main(String[] args) {
		PagingUtil paging;
		/*paging = new PagingUtil(0, 0);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(1, 0);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(5, 0);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(5, 1);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(10, 0);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(10, 1);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(80, 6);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(80, 7);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(80, 8);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(85, 9);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(80, 9);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(45, 5);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(80, 10);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(85, 9);
		System.out.println("\n" + paging);
		*/
		/*paging = new PagingUtil(200, 1);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 4);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 5);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 8);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 10);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 12);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 15);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 18);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 20);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 25);
		System.out.println("\n" + paging);*/
	}

	@Override
	public String toString() {
		return "currentPageNo: " + this.currentPageNo + "\n" +
						"itemsPerPage: " + this.itemsPerPage + "\n" +
						"totalItems: " + this.totalItems + "\n" +
						"totalPages: " + this.totalPages + "\n" +
						"Page Items: " + this.pages + "\n" +
						"hasPrev: " + this.hasPrev + "\n" +
						"hasNext: " + this.hasNext + "\n" +
						"hasLeftDots: " + this.hasLeftDots + "\n" +
						"hasRightDots: " + this.hasRightDots + "\n" +
						"showFirst: " + this.showFirst + "\n" +
						"showLast: " + this.showLast;
	}

	public boolean isShowFirst() {
		return showFirst;
	}

	public void setShowFirst(boolean showFirst) {
		this.showFirst = showFirst;
	}

	public boolean isShowLast() {
		return showLast;
	}

	public void setShowLast(boolean showLast) {
		this.showLast = showLast;
	}

	public int getStartingItemNo() {
		return startingItemNo;
	}

	public void setStartingItemNo(int startingItemNo) {
		this.startingItemNo = startingItemNo;
	}
	

}
/*

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.ivant.cms.entity.User;

*//** Utility to create pagination
 * 
 * @author Allan
 * @version 1.0
 *//*
public final class PagingUtil {
	
	public static final int DEFAULT_ITEMS_PER_PAGE = 10;
	public static final int DEFAULT_ITEMS_PER_PAGE_CMS = 10;
	public static final int DEFAULT_PAGE_NUMBERS_TO_DISPLAY = 20;
	
	private List<Integer> pages;
	private int totalPages; 
	private int totalItems;
	private User user;
	private int itemsPerPage;
	private int currentPageNo;
	private int pageNumbersToDisplay;
	private int halfPageNumbersToDisplay;
	
	private boolean hasPrev;
	private boolean hasNext;
	
	private boolean hasLeftDots;
	private boolean hasRightDots;
	
	private boolean showFirst;
	private boolean showLast;
	
	private int startingItemNo;
	
	
	public void setUser(User user) {
		this.user = user;
	}
	
	
	*//** Create pagination with default current page is 1 and DEFAULT_PAGE_NUMBERS_TO_DISPLAY, DEFAULT_ITEMS_PER_PAGE attribute
	 * @param totalItems
	 *//*
	public PagingUtil(){
		pages = new LinkedList<Integer>();		
	}
	
	public PagingUtil(int totalItems) {
		this(totalItems, 1);
	}
	
	*//** Create pagination with DEFAULT_PAGE_NUMBERS_TO_DISPLAY, DEFAULT_ITEMS_PER_PAGE attribute
	 * @param totalItems
	 * @param currentPageNo
	 *//*
	public PagingUtil(int totalItems, int currentPageNo) {
		this(totalItems, DEFAULT_ITEMS_PER_PAGE, currentPageNo, DEFAULT_PAGE_NUMBERS_TO_DISPLAY);
	}
	
	*//** Create pagination with DEFAULT_PAGE_NUMBERS_TO_DISPLAY attribute
	 * @param totalItems
	 * @param itemsPerPage
	 * @param currentPageNo
	 *//*
	public PagingUtil(int totalItems, int itemsPerPage, int currentPageNo) {
		this(totalItems, itemsPerPage, currentPageNo, DEFAULT_PAGE_NUMBERS_TO_DISPLAY);
	}
	
	*//** Create pagination with full control of the attributes
	 * @param totalItems total items in the list
	 * @param itemsPerPage items to be displayed per page
	 * @param currentPageNo current selected page number 
	 * @param pageNumbersToDisplay numbers of pages to display etc. [1 2 3 4 5] has 5 pageNumbersToDisplay
	 *//*
	public PagingUtil(int totalItems, int itemsPerPage, int currentPageNo, int pageNumbersToDisplay ) {
		this.totalItems = totalItems;
		this.itemsPerPage = itemsPerPage;
		this.currentPageNo = currentPageNo;
		if(pageNumbersToDisplay == -1)
			this.pageNumbersToDisplay = DEFAULT_PAGE_NUMBERS_TO_DISPLAY;
		else this.pageNumbersToDisplay = pageNumbersToDisplay;
		halfPageNumbersToDisplay = pageNumbersToDisplay / 2;
		if(pageNumbersToDisplay % 2 != 0){
			halfPageNumbersToDisplay++;
		}
		pages = new LinkedList<Integer>();
		this.process();
	}
	
	*//**
	 * @return currently selected page number
	 *//*
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	
	*//**
	 * @return items per page
	 *//*
	public int getItemsPerPage() {
		return itemsPerPage;
	}
	
	*//**
	 * @return page numbers to display
	 *//*
	public int getPageNumbersToDisplay() {
		return pageNumbersToDisplay;
	}

	*//**
	 * @return list of pages to be displayed
	 *//*
	public List<Integer> getPages() {
		return pages;
	}

	*//**
	 * @return overall total items in the list
	 *//*
	public int getTotalItems() {
		return totalItems;
	}

	*//**
	 * @return calculated total pages
	 *//*
	public int getTotalPages() {
		return totalPages;
	}

	*//** Determine if there are lower pages exist but not displayed.
	 * @return true if left dots (...) is to be displayed; otherwise false
	 *//*
	public boolean isHasLeftDots() {
		return hasLeftDots;
	}


	*//**
	 * @return true if it has next page; otherwise false
	 *//*
	public boolean isHasNext() {
		return hasNext;
	}

	*//**
	 * @return true if it has previous page; otherwise false
	 *//*
	public boolean isHasPrev() {
		return hasPrev;
	}

	*//** Determine if there are higher pages exist but not displayed
	 * @return true if it has right dots (...); otherwise false
	 *//*
	public boolean isHasRightDots() {
		return hasRightDots;
	}

	public void doPaginate(){
		process();
	}
	*//**
	 *  Calculation of pagination
	 *//*
	private void process() {
		if(totalItems == 0 || itemsPerPage == 0) {
			return;
		}
		
		if(currentPageNo == 0) {
			currentPageNo++;
		}
		//System.out.println("3  ItemsPerPage,  TotalItems :  " + itemsPerPage + "  " + totalItems);
		totalPages = totalItems / itemsPerPage;
		if( (totalItems % itemsPerPage) > 0 ){
			totalPages++;
		}
		
		if (currentPageNo > 1)
			hasPrev = true;
		
		if(currentPageNo < totalPages )
			hasNext = true;
		
		if( Math.min(totalPages, currentPageNo) < pageNumbersToDisplay ||
				totalPages == pageNumbersToDisplay) {
			for(int i = 1; i <= Math.min(totalPages, pageNumbersToDisplay); i++) {
				pages.add(i);
			}
//			if(pages.size() > 0 && pages.get(0) > pageNumbersToDisplay){
//				if(pages.size() > 0 && pages.get(0) > pageNumbersToDisplay){
//				showFirst = true;
//			}
		}
		else {
			int pageCounterUp = 0;
			for(int i = 0; i < halfPageNumbersToDisplay; i++  ){
				pages.add(i + currentPageNo);
				pageCounterUp++;
				if(currentPageNo + pageCounterUp > totalPages)
					break;
			}
			int pageCounterDown = pageNumbersToDisplay - pageCounterUp;
			for (int i = 1; i <= pageCounterDown; i++) {
				pages.add(currentPageNo - i);
			}
			
			Collections.sort(pages);
			
			
			int startingPage = 1;
			int loop = currentPageNo / (pageNumbersToDisplay);
			
			if(currentPageNo % pageNumbersToDisplay == 0)
				loop--;
			
			for(int i = 0; i < loop; i++) {
				startingPage+=pageNumbersToDisplay;
			}
			int excess = totalPages - startingPage;
			for(int i= 0, j = startingPage; i <= excess && i < pageNumbersToDisplay ; i++) {
				pages.add(j++);
			}
			
		}
		
		if(pages.size() > 0){
			int first = pages.get(0);
			if(first > 1){
				hasLeftDots = true;
				//if(showFirst != true && first > halfPageNumbersToDisplay ){
					showFirst = true;
//				}
			}
			int last = pages.get( pages.size() - 1);
			if(last < totalPages ){
				hasRightDots = true;
//				if(last + halfPageNumbersToDisplay < totalPages){
					showLast = true;
//				}
			}
		}
		
		startingItemNo = (currentPageNo - 1) * itemsPerPage;
		
		return;
	}

	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}

	public void setHasLeftDots(boolean hasLeftDots) {
		this.hasLeftDots = hasLeftDots;
	}
	
	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public void setHasPrev(boolean hasPrev) {
		this.hasPrev = hasPrev;
	}

	public void setHasRightDots(boolean hasRightDots) {
		this.hasRightDots = hasRightDots;
	}

	public void setItemsPerPage(int itemsPerPage) {
		this.itemsPerPage = itemsPerPage;
	}

	public void setPageNumbersToDisplay(int pageNumbersToDisplay) {
		this.pageNumbersToDisplay = pageNumbersToDisplay;
		halfPageNumbersToDisplay = pageNumbersToDisplay / 2;
		if(pageNumbersToDisplay % 2 != 0){
			halfPageNumbersToDisplay++;
		}
	}

	public void setPages(List<Integer> pages) {
		this.pages = pages;
	}

	public void setTotalItems(int totalItems) {
		this.totalItems = totalItems;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	@Override
	public String toString() {
		return "currentPageNo: " + this.currentPageNo + "\n" +
						"itemsPerPage: " + this.itemsPerPage + "\n" +
						"totalItems: " + this.totalItems + "\n" +
						"totalPages: " + this.totalPages + "\n" +
						"Page Items: " + this.pages + "\n" +
						"hasPrev: " + this.hasPrev + "\n" +
						"hasNext: " + this.hasNext + "\n" +
						"hasLeftDots: " + this.hasLeftDots + "\n" +
						"hasRightDots: " + this.hasRightDots + "\n" +
						"showFirst: " + this.showFirst + "\n" +
						"showLast: " + this.showLast;
	}

	public boolean isShowFirst() {
		return showFirst;
	}

	public void setShowFirst(boolean showFirst) {
		this.showFirst = showFirst;
	}

	public boolean isShowLast() {
		return showLast;
	}

	public void setShowLast(boolean showLast) {
		this.showLast = showLast;
	}

	public int getStartingItemNo() {
		return startingItemNo;
	}

	public void setStartingItemNo(int startingItemNo) {
		this.startingItemNo = startingItemNo;
	}
	

	
	*//**
	 * used in testing
	 * @param args
	 *//*
	public static void main(String[] args) {
		PagingUtil paging;
		paging = new PagingUtil(0, 0);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(1, 0);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(5, 0);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(5, 1);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(10, 0);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(10, 1);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(80, 6);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(80, 7);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(80, 8);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(85, 9);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(80, 9);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(45, 5);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(80, 10);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(85, 9);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 1);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 4);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 5);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 8);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 10);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 12);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 15);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 18);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 20);
		System.out.println("\n" + paging);
		
		paging = new PagingUtil(200, 25);
		System.out.println("\n" + paging);
	}

}*/
