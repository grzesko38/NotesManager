package pl.arczynskiadam.core.exception;

public class EmailUnavailableException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EmailUnavailableException() {}
	
	public EmailUnavailableException(String msg) {
		super(msg);
	}
}
