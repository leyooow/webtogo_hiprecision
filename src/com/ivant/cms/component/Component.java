package com.ivant.cms.component;

import javax.servlet.http.HttpServletRequest;

public interface Component {

	String getName();
	void prepareComponent(HttpServletRequest request);
}
