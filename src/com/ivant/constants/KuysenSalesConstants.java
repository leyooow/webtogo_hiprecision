package com.ivant.constants;

import java.util.HashMap;
import java.util.Map;

import com.ivant.cms.beans.transformers.KuysenClientTransformer;
import com.ivant.cms.beans.transformers.KuysenSalesAreaTransformer;
import com.ivant.cms.beans.transformers.KuysenSalesOptionalSetTransformer;
import com.ivant.cms.beans.transformers.KuysenSalesOrderSetTransformer;
import com.ivant.cms.beans.transformers.KuysenSalesParentOrderSetTransformer;
import com.ivant.cms.beans.transformers.KuysenSalesTransactionTransformer;
import com.ivant.cms.beans.transformers.base.Transformer;
import com.ivant.cms.entity.KuysenSalesClient;
import com.ivant.cms.entity.KuysenSalesTransaction;

public class KuysenSalesConstants {
	public static final String KUYSEN_CLIENT = "kuysen_client";
	
	public static final String KUYSEN_CLIENT_BEAN = "client";
	
	public static final String KUYSEN_TRANSACTION = "kuysen_transaction";
	
	public static final String NO_CLIENT_TO_PROCESS = "no_client";
	
	public static final Long PRODUCTS_CATEGORY_ID = 5783L;
	
	public static final Long KUYSEN_MAIN_COMPANY_ID = 321L;
	
	public static final String CATEGORY_IMAGES_DIRECTORY = "/images/items/";
	
	public static final Integer CATEGORY_ITEMS_MAX_ITEMS_PER_PAGE = 10;
	
	public static final String CATEGORY_ITEM_FETCH_STATUS_OK = "ok";
	
	public static final String CATEGORY_ITEM_FETCH_STATUS_NO_ITEM = "no item available";
	
	public static final String CATEGORY_ITEM_FETCH_STATUS_LAST_PAGE = "last page";
	
	public static final Integer PRODUCT_INITIAL_QUANTITY = 1;
	
	public static final String QUOTATION_JASPER_FILE_NAME = "KuysenSalesQuotation.jrxml";
	
	public static final String QUOTATION_PDF_FOLDER = "kuysen_sales_pdf";
	
	public static final String QUOTATION_PDF_FILENAME_PREFIX = "Kuysen Sales Quotation";
	
	public static final Map<Class<?>, Transformer> TRANSFORMERS = new HashMap<Class<?>, Transformer>() {{
		put(KuysenClientTransformer.class, new KuysenClientTransformer());
		put(KuysenSalesTransactionTransformer.class, new KuysenSalesTransactionTransformer());
		put(KuysenSalesAreaTransformer.class, new KuysenSalesAreaTransformer());
		put(KuysenSalesParentOrderSetTransformer.class, new KuysenSalesParentOrderSetTransformer());
		put(KuysenSalesOrderSetTransformer.class, new KuysenSalesOrderSetTransformer());
		put(KuysenSalesOptionalSetTransformer.class, new KuysenSalesOptionalSetTransformer());
	}};
	
}
