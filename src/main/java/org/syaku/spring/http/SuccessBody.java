package org.syaku.spring.http;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2016. 9. 21.
 */
public class SuccessBody {

	private String message;
	private boolean error = false;
	private Object data;
	private StatusCode statusCode = StatusCode.OK;

	public SuccessBody() {
	}

	public SuccessBody(String message) {
		this.message = message;
	}

	public SuccessBody(String message, boolean error) {
		this.message = message;
		this.error = error;
	}

	public SuccessBody(String message, boolean error, Object data) {
		this.message = message;
		this.error = error;
		setData(data);
	}

	public SuccessBody(String message, boolean error, StatusCode statusCode) {
		this.message = message;
		this.error = error;
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public StatusCode getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(StatusCode statusCode) {
		this.statusCode = statusCode;
	}

	@Override
	public String toString() {
		return "SuccessBody{" +
				"message='" + message + '\'' +
				", error=" + error +
				", data=" + data +
				", statusCode=" + statusCode +
				'}';
	}
}
