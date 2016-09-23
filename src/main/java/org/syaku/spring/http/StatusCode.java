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
 * 404 : 비밀번호 기간이 만료됨.
 * 450 : 폼 유효성검사
 *
 */
public enum StatusCode {
	OK(200),
	Unauthorized(401),
	AccessDenied(402),
	ConcurrentSession(403),
	PasswordUseExpired(404),
	FormValidation(450);

	private final int value;

	StatusCode(int value) {
		this.value = value;
	}

	public int value() {
		return value;
	}
}