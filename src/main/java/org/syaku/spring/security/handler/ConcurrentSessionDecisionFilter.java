package org.syaku.spring.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.filter.GenericFilterBean;
import org.syaku.snack.HttpSnack;
import org.syaku.snack.RequestSnack;
import org.syaku.spring.http.StatusCode;
import org.syaku.spring.http.SuccessBody;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 22.
 */
public class ConcurrentSessionDecisionFilter extends GenericFilterBean {

	private final SessionRegistry sessionRegistry;
	private final String decisionUrl;
	private boolean chain = true;
	private String ignoreParameterName = "ignore";

	public ConcurrentSessionDecisionFilter(SessionRegistry sessionRegistry, String decisionUrl) {
		this.sessionRegistry = sessionRegistry;
		this.decisionUrl = decisionUrl;
	}

	public void setIgnoreParameterName(String ignoreParameterName) {
		this.ignoreParameterName = ignoreParameterName;
	}

	public void setChain(boolean chain) {
		this.chain = chain;
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

		System.out.println(" 요청됨  ,,, 됨 ====>>>>>>>>>>>>>>>>>>>>");

		HttpServletRequest request = HttpSnack.getHttpServletRequest(req);
		HttpServletResponse response = HttpSnack.getHttpServletResponse(res);

		HttpSession session = request.getSession(false);

		if (session != null) {
			SessionInformation info = sessionRegistry.getSessionInformation(session.getId());

			if (info != null) {
				String ignore = request.getParameter(ignoreParameterName);

				if (StringUtils.isNotEmpty(ignore)) {
					System.out.println("무시됨 ====>>>>>>>>>>>>>>>>>>>>");
					chain.doFilter(req, res);
					return;
				}

				if(RequestSnack.isAjax(request)) {
					response.setContentType("application/json");
					response.setCharacterEncoding("utf-8");

					SuccessBody success = new SuccessBody();
					success.setError(true);
					success.setStatusCode(StatusCode.ConcurrentSession);

					ObjectMapper objectMapper = new ObjectMapper();
					String json = objectMapper.writeValueAsString(success);
					PrintWriter out = response.getWriter();
					out.print(json);
					out.flush();
					out.close();
				} else {

					if (this.chain) {
						request.getRequestDispatcher(request.getContextPath() + decisionUrl).forward(request, response);
					} else {
						response.sendRedirect(request.getContextPath() + decisionUrl);
					}
				}
			}
		} else {
			System.out.println("작동안함... ====>>>>>>>>>>>>>>>>>>>>");
			chain.doFilter(req, res);
		}


	}

}
