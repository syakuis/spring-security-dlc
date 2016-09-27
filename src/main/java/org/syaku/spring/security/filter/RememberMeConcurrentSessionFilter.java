package org.syaku.spring.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.rememberme.RememberMeAuthenticationFilter;
import org.springframework.security.web.authentication.session.NullAuthenticatedSessionStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 27.
 */
public class RememberMeConcurrentSessionFilter extends RememberMeAuthenticationFilter {
	private SessionAuthenticationStrategy sessionStrategy = new NullAuthenticatedSessionStrategy();

	/**
	 * RememberMe 설정에서는 동시 세션 제어를 직집 주입해줘야 한다.
	 *
	 * @param sessionStrategy the session strategy
	 */
	public void setSessionAuthenticationStrategy(SessionAuthenticationStrategy sessionStrategy) {
		this.sessionStrategy = sessionStrategy;
	}

	public RememberMeConcurrentSessionFilter(AuthenticationManager authenticationManager, RememberMeServices rememberMeServices) {
		super(authenticationManager, rememberMeServices);
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		super.doFilter(req, res, chain);
	}


	@Override
	public void onSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult) {
		sessionStrategy.onAuthentication(authResult, request, response);
	}
}
