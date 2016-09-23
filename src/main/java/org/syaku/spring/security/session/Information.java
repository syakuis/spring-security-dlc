package org.syaku.spring.security.session;

import org.springframework.security.core.userdetails.User;

import java.util.Date;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 22.
 */
public class Information {
	private final String username;
	private final String sessionId;
	private final Date lastRequest;
	private final Object principal;

	public Information(String username, String sessionId, Date lastRequest, Object principal) {
		this.username = username;
		this.sessionId = sessionId;
		this.lastRequest = lastRequest;
		this.principal = principal;
	}

	public String getUsername() {
		return username;
	}

	public String getSessionId() {
		return sessionId;
	}

	public Date getLastRequest() {
		return lastRequest;
	}

	public Object getPrincipal() {
		return principal;
	}
}
