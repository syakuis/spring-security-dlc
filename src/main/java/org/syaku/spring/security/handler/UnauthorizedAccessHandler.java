package org.syaku.spring.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.syaku.snack.RequestSnack;
import org.syaku.spring.http.SuccessBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 인증(Consumer)되지 않은 사용자가 허가되지 않은 페이지에 접근할때 요청되는 헨들러.
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 16. 5. 30.
 */
public class UnauthorizedAccessHandler implements AuthenticationEntryPoint {

	private final String loginFormUrl;
	private boolean chain = true;

	public UnauthorizedAccessHandler(String loginFormUrl) {
		this.loginFormUrl = loginFormUrl;
	}

	public void setChain(boolean chain) {
		this.chain = chain;
	}

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		if(RequestSnack.isAjax(request)) {
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			SuccessBody success = new SuccessBody();

			success.setMessage(exception.getMessage());
			success.setError(true);

			ObjectMapper objectMapper = new ObjectMapper();
			String data = objectMapper.writeValueAsString(success);
			PrintWriter out = response.getWriter();
			out.print(data);
			out.flush();
			out.close();
		} else {

			if (chain) {
				request.getRequestDispatcher(request.getContextPath() + loginFormUrl).forward(request, response);
			} else {
				response.sendRedirect(request.getContextPath() + loginFormUrl);
			}
		}
	}
}
