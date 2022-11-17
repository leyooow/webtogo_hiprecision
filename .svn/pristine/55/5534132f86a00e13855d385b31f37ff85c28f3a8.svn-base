package com.ivant.cms.ws.rest.client;

import java.io.*;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.json.simple.JSONObject;

import com.ivant.utils.HMAC;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.representation.Form;

public class CouponHistoryClient extends AbstractBaseClient {
	private static final String HEADER_NAME_SHARED_SECRET = "x-shared-secret";
	private static final String PRIVATE_KEY = "73b6f5a9135a989d0ea6";
	private static final String HEADER_NAME_PUBLIC_KEY = "x-public-key";
	private static final Object PUBLIC_KEY = "4mwu2QScXGEXqqHFrFde";

	private static CouponHistoryClient instance;

	public CouponHistoryClient() {
		super();
	}

	public static final CouponHistoryClient getInstance() {
		if (instance == null) {
			instance = new CouponHistoryClient();
		}
		return instance;
	}

	@SuppressWarnings("unchecked")
	public String findAllPurchaseBySubscriberMsisdn(String subscriberMsisdn) {

		Client client = Client.create();
		WebResource service = client
				.resource("http://admin.pocketpons.com/rest/couponItem/findAllPurchaseBySubscriberMsisdn");
		final Form form = new Form();
		form.add("subscriberMsisdn", subscriberMsisdn);

		final String dtRequest = DateTimeFormat.forPattern("MMddYYYYHHmmss")
				.print(new DateTime());
		final Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		parameterMap.put("dtRequest", new String[] { dtRequest });
		final String key = HMAC.sign(PRIVATE_KEY, parameterMap);
		service = service.queryParam("dtRequest", dtRequest);
		final ClientResponse clientResponse = service
				.accept(MediaType.APPLICATION_JSON)
				.header(HEADER_NAME_PUBLIC_KEY, PUBLIC_KEY)
				.header(HEADER_NAME_SHARED_SECRET, key)
				.post(ClientResponse.class, form);
		
		
		if (clientResponse.getStatus() == HttpServletResponse.SC_OK
				|| clientResponse.getStatus() == HttpServletResponse.SC_NO_CONTENT) {
			return clientResponse.getEntity(String.class);
		} else {
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("success", Boolean.FALSE.toString());
			
			return jsonObject.toJSONString();
		}
	}

}
