package org.syaku.snack;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 21.
 */
public class RequestSnack {
	private RequestSnack() {}

	public static String getPathQueryString(HttpServletRequest request) {
		String servlet_path = request.getServletPath();
		String query_string = request.getQueryString();
		query_string = ( StringUtils.isEmpty(query_string) ) ? "" : "?" + query_string;
		String servlet_url = servlet_path + query_string;

		return servlet_url;
	}

	public static boolean isAjax(HttpServletRequest request) {
		String accept = request.getHeader("accept");
		String ajax = request.getHeader("X-Requested-With");

		return ( StringUtils.indexOf(accept, "json") > -1 && StringUtils.isNotEmpty(ajax));
	}
}
