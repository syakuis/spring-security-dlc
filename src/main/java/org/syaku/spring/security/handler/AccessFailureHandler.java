package org.syaku.spring.security.handler;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.syaku.snack.RequestSnack;
import org.syaku.spring.http.SuccessBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 인증(Consumer)된 사용자가 허가되지 않은 페이지에 접근할때 호출되는 헨들러.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 5. 30.
 */
public class AccessFailureHandler implements AccessDeniedHandler {

	private final String loginFormUrl;
	private final String errorPage;
	private String redirectUrlParameter = "redirect_url";
	private boolean chain = true;

	public AccessFailureHandler(String loginFormUrl, String errorPage) {
		this.loginFormUrl = loginFormUrl;
		this.errorPage = errorPage;
	}

	public void setChain(boolean chain) {
		this.chain = chain;
	}

	public void setRedirectUrlParameter(String redirectUrlParameter) {
		this.redirectUrlParameter = redirectUrlParameter;
	}

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
		String url = RequestSnack.getPathQueryString(request);

		if(RequestSnack.isAjax(request)) {
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");

			SuccessBody success = new SuccessBody(exception.getMessage(), true);

			ObjectMapper objectMapper = new ObjectMapper();
			String data = objectMapper.writeValueAsString(success);

			PrintWriter out = response.getWriter();
			out.print(data);
			out.flush();
			out.close();
		} else {

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			if (chain) {
				request.setAttribute("message", exception.getMessage());
				request.setAttribute(redirectUrlParameter, url);
				request.setAttribute("loginFormUrl", loginFormUrl);
				request.getRequestDispatcher(request.getContextPath() + errorPage).forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + errorPage);
			}
		}
	}
}