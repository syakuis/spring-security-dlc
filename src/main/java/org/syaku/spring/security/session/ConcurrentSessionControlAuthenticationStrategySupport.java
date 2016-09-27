package org.syaku.spring.security.session;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.session.ConcurrentSessionControlAuthenticationStrategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 22.
 */
public class ConcurrentSessionControlAuthenticationStrategySupport extends ConcurrentSessionControlAuthenticationStrategy {

	public ConcurrentSessionControlAuthenticationStrategySupport(SessionRegistry sessionRegistry, int maximumSessions, boolean exceptionIfMaximumExceeded) {
		super(sessionRegistry);
		super.setMaximumSessions(maximumSessions);
		super.setExceptionIfMaximumExceeded(exceptionIfMaximumExceeded);
	}

	/**
	 * 중복로그인 비활성화인 경우 동시 세션 설정을 수정한다.
	 *
	 * @param duplicationLoginDisable the duplication login disable
	 */
	public void setDuplicationLoginDisable(boolean duplicationLoginDisable) {
		if (duplicationLoginDisable) {
			super.setMaximumSessions(1);
			super.setExceptionIfMaximumExceeded(false);
		}
	}

	@Override
	public void onAuthentication(Authentication authentication, HttpServletRequest request, HttpServletResponse response) {
		super.onAuthentication(authentication, request, response);
	}
}
