package exception;

public class BlogApiException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String HttpStatus;
	private String message;
	
	public BlogApiException() {
		// TODO Auto-generated constructor stub
	}
	
	public BlogApiException(String httpStatus, String message) {
		super(message);
		HttpStatus = httpStatus;
		this.message = message;
	}

	public String getHttpStatus() {
		return HttpStatus;
	}

	public void setHttpStatus(String httpStatus) {
		HttpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
	
	
	
}
