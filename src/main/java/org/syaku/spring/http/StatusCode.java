package org.syaku.spring.http;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http ://syaku.tistory.com
 * @since 16. 8. 19.
 *
 *
 * 200 : 성공
 * 401 : 로그인하지 않음.
 * 402 : 접근권한이 없음.
 * 403 : 중복로그인.
 * 450 : 폼 유효성검사
 *
 */
public enum StatusCode {
	OK(200),
	Unauthorized(401),
	AccessDenied(402),
	ConcurrentSession(403),
	FormValidation(450);

	private final int code;

	StatusCode(int code) {
		this.code = code;
	}

	public int getCode() { return code; }
}