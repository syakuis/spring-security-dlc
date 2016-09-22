package org.syaku.spring.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;
import org.syaku.snack.RequestSnack;
import org.syaku.spring.http.SuccessBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 21.
 */
public class SignInSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	private boolean chain = true;

	public void setChain(boolean chain) {
		this.chain = chain;
	}

	private String redirectUrl(HttpServletRequest request, HttpServletResponse response) {
		HttpSessionRequestCache requestCache = new HttpSessionRequestCache();
		SavedRequest savedRequest = requestCache.getRequest(request, response);

		if (savedRequest == null) {
			return determineTargetUrl(request, response);
		}

		String targetUrlParameter = getTargetUrlParameter();
		if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
			requestCache.removeRequest(request, response);

			return determineTargetUrl(request, response);
		}

		clearAuthenticationAttributes(request);
		return savedRequest.getRedirectUrl();
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		if(RequestSnack.isAjax(request)) {
			String redirectUrl = redirectUrl(request, response);

			if (logger.isDebugEnabled()) {
				logger.debug("redirectUrl: " + redirectUrl);
				logger.debug("targetUrlParameter name: " + getTargetUrlParameter());
				logger.debug("targetUrlParameter value: " + request.getParameter(getTargetUrlParameter()));
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			response.setStatus(HttpServletResponse.SC_OK);

			SuccessBody success = new SuccessBody();

			success.setError(false);
			success.setMessage(null);

			Map<String, Object> data = new HashMap<>();
			data.put("chain", chain);
			data.put("redirectUrl", redirectUrl);
			success.setData(data);

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(success);

			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} else {
			super.onAuthenticationSuccess(request, response, authentication);
		}
	}
}