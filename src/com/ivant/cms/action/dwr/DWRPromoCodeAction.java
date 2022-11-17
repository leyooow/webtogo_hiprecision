package com.ivant.cms.action.dwr;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ivant.cms.action.admin.dwr.AbstractDWRAction;
import com.ivant.cms.entity.PromoCode;

public class DWRPromoCodeAction extends AbstractDWRAction{

	public PromoCode findPromoCode(String code) {
		return promoCodeDelegate.findByCode(company, code);
	}
	
	public String validateHiprePromoCode(String codes, String cartItemIds, String cartItemNames) {
		Date now = Calendar.getInstance().getTime();
		String[] promoCodes = codes.split(",");
		List<PromoCode> promoCodeList = promoCodeDelegate.findAll(company).getList();
		Map<String, String> itemMap = new HashMap<String, String>();
		Map<String, List<String>> cartItemMap = new HashMap<String, List<String>>();
		boolean valid = false;
		boolean expired = true;
		String response = "";
		int promoCodesLength = promoCodes.length;
		for(int i = 0; i<promoCodes.length; i++) {
			valid = false;
			expired = true;
			for(PromoCode promoCode : promoCodeList) {
				String promoCodeStr = promoCodes[i].trim().toLowerCase();
				String codeStr = promoCode.getCode().trim().toLowerCase();
				if(codeStr.equalsIgnoreCase(promoCodeStr)) {
					valid = true;
					Calendar calendar = Calendar.getInstance();
				    calendar.setTime(promoCode.getToDate().toDate());
				    calendar.set(Calendar.HOUR_OF_DAY, 23);
				    calendar.set(Calendar.MINUTE, 59);
				    calendar.set(Calendar.SECOND, 59);
				    calendar.set(Calendar.MILLISECOND, 999);
				    Date to = calendar.getTime();
					if(now.after(promoCode.getFromDate().toDate()) && now.before(to)) {
						expired = false;
						boolean hasItems = false;
						String[] ids = cartItemIds.split(",");
						String[] names = cartItemNames.split(",");
						if(promoCode.getItems().contains("AllItems")) {
							hasItems = true;
							for(int x = 0; x<ids.length; x++) {
								if(itemMap.get(ids[x]) == null) {
									itemMap.put(ids[x], promoCodes[i]);
									//hasItems = true;
								}
								if(cartItemMap.get(ids[x]+"&&"+names[x]) == null) {
									List<String> stringList = new ArrayList<String>();
									stringList.add(promoCodeStr);
									cartItemMap.put(ids[x]+"&&"+names[x], stringList);
								} else {
									List<String> stringList = cartItemMap.get(ids[x]+"&&"+names[x]);
									stringList.add(promoCodeStr);
									cartItemMap.put(ids[x]+"&&"+names[x], stringList);
								}
							}
							if(!hasItems) {
								response += promoCodes[i] + " - not valid <br/>";
							}
							break;
						}
						for(int x = 0; x<ids.length; x++) {
							if(promoCode.getItems().contains(ids[x])) {
								hasItems = true;
								if(itemMap.get(ids[x]) == null) {
									itemMap.put(ids[x], promoCodes[i]);
									//hasItems = true;
								}
								if(cartItemMap.get(ids[x]+"&&"+names[x]) == null) {
									List<String> stringList = new ArrayList<String>();
									stringList.add(promoCodeStr);
									cartItemMap.put(ids[x]+"&&"+names[x], stringList);
								} else {
									List<String> stringList = cartItemMap.get(ids[x]+"&&"+names[x]);
									stringList.add(promoCodeStr);
									cartItemMap.put(ids[x]+"&&"+names[x], stringList);
								}
							}
						}
						if(!hasItems) {
							response += promoCodes[i] + " - not valid <br/>";
						}
					}
				}
			}
			
			if(!valid) {
				response += promoCodes[i] + " - does not exist <br/>";
			}
			if(valid && expired) {
				response += promoCodes[i] + " - has been expired <br/>";
			}
			
		}
		
		Iterator entries = cartItemMap.entrySet().iterator();
		while (entries.hasNext()) {
			Entry thisEntry = (Entry) entries.next();
			String key = (String) thisEntry.getKey();
			List<String> value = (List<String>) thisEntry.getValue();
			if(value.size() > 1) {
				String promoNameStr = "";
				for(String promoId : value) {
					promoNameStr = (promoNameStr.equals("") ? promoId : promoNameStr+", "+promoId);
				}
				response +=  promoNameStr + " - Invalid promo code combination for "+ key.split("&&")[1] + "<br/>";
			}
		}
		
		return response;
	}
	
}
