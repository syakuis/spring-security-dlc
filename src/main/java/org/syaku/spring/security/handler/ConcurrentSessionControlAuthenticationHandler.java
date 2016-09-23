package org.syaku.spring.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;
import org.syaku.snack.RequestSnack;
import org.syaku.spring.http.StatusCode;
import org.syaku.spring.http.SuccessBody;
import org.syaku.spring.security.exception.ConcurrentSessionException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 22.
 */
public class ConcurrentSessionControlAuthenticationHandler extends ConcurrentSessionControlAuthenticationStrategy {
	private final SessionRegistry sessionRegistry;
	private boolean chain = true;
	private String decisionUrl;
	private String decisionParameterName = "decision";

	public ConcurrentSessionControlAuthenticationHandler(SessionRegistry sessionRegistry) {
		super(sessionRegistry);
		this.sessionRegistry = sessionRegistry;
	}

	/**
	 * ajax 방식이 아닌 경우 페이지 요청 방식
	 *
	 * @param chain
	 */
	public void setChain(boolean chain) {
		this.chain = chain;
	}

	/**
	 * ajax 방식이 아닌 경우 중복로그인 여부를 묻는 페이지에 대한 url
	 *
	 * @param decisionUrl 페이지 url
	 */
	public void setDecisionUrl(String decisionUrl) {
		this.decisionUrl = decisionUrl;
	}

	/**
	 * 중복로그인 여부를 판단하기 위한 파라메터명
	 *
	 * @param decisionParameterName 파라메터명
	 */
	public void setDecisionParameterName(String decisionParameterName) {
		this.decisionParameterName = decisionParameterName;
	}

	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		final List<SessionInformation> sessions = sessionRegistry.getAllSessions(authentication.getPrincipal(), false);

		System.out.println(" 요청됨  ,,, 됨 ====>>>>>>>>>>>>>>>>>>>>" + sessions.size());
		System.out.println(" 요청됨  ,,, 됨 ====>>>>>>>>>>>>>>>>>>>>" + authentication.getPrincipal());

		int allowedSessions = getMaximumSessionsForThisUser(authentication);

		if (concurrentSessionDecision(sessions, allowedSessions, request)) {
			try {
				RequestDispatcher dispatcher = request.getRequestDispatcher(request.getContextPath() + decisionUrl);
				dispatcher.forward(request, response);
				return;
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (ServletException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}

		//allowableSessionsExceeded(sessions, allowedSessions, sessionRegistry);

		/*String ignore = request.getParameter(decisionParameterName);
		boolean isIgnore = Boolean.getBoolean(ignore);

		if (concurrentSessionDecision(sessions, allowedSessions, request) && !isIgnore) {
			System.out.println("============= .....!!!!");
			this.onConcurrentSessionControlAuthentication(request, response);
			return;
		}

		if (isIgnore) {
			System.out.println("============= 로그인 처리.....");
			allowableSessionsExceeded(sessions, allowedSessions, sessionRegistry);
			return;
		}*/
	}

	public void onConcurrentSessionControlAuthentication(HttpServletRequest request, HttpServletResponse response) {
		try {
			if(RequestSnack.isAjax(request)) {
				System.out.println("============= ajax");

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
				System.out.println("============= http");
				if (this.chain) {
					request.getRequestDispatcher(request.getContextPath() + decisionUrl).forward(request, response);
				} else {
					response.sendRedirect(request.getContextPath() + decisionUrl);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		} catch (ServletException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	/**
	 * 이미 생성된 세션을 설정된 규칙에 따라 조회한다.
	 *
	 * @param sessions        the sessions
	 * @param allowedSessions the allowed sessions
	 * @param request         the request
	 * @return the boolean
	 *
	 * @see super.onAuthentication
	 */
	private boolean concurrentSessionDecision(List<SessionInformation> sessions, int allowedSessions, HttpServletRequest request) {
		int sessionCount = sessions.size();
		if (sessionCount < allowedSessions) {
			return false;
		}

		if (allowedSessions == -1) {
			return false;
		}

		if (sessionCount == allowedSessions) {
			HttpSession session = request.getSession(false);

			if (session != null) {
				for (SessionInformation si : sessions) {
					if (si.getSessionId().equals(session.getId())) {
						return false;
					}
				}
			}
		}

		return true;
	}
}
