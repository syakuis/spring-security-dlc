package org.syaku.spring.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.syaku.snack.RequestSnack;
import org.syaku.spring.http.SuccessBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 로그아웃 성공 후 호출되는 헨들러
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 5. 30.
 */
public class SignOutSuccessHandler extends AbstractAuthenticationTargetUrlRequestHandler implements LogoutSuccessHandler {

	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

		if(RequestSnack.isAjax(request)) {
			String targetUrl = determineTargetUrl(request, response);

			if (logger.isDebugEnabled()) {
				logger.debug("targetUrl: " + targetUrl);
				logger.debug("targetUrlParameter name: " + getTargetUrlParameter());
				logger.debug("targetUrlParameter value: " + request.getParameter(getTargetUrlParameter()));
			}

			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");

			SuccessBody success = new SuccessBody();

			response.setStatus(HttpServletResponse.SC_OK);

			success.setError(false);
			success.setData(targetUrl);

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(success);

			PrintWriter out = response.getWriter();
			out.print(json);
			out.flush();
			out.close();
		} else {
			super.handle(request, response, authentication);
		}

	}
}