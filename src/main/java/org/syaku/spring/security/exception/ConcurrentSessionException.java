package org.syaku.spring.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 22.
 */
public class ConcurrentSessionException extends AuthenticationException {

	public ConcurrentSessionException(String msg) {
		super(msg);
	}
}
