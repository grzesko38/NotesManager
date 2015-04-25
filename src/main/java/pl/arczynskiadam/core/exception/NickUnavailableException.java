package pl.arczynskiadam.core.exception;

public class NickUnavailableException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NickUnavailableException() {}
	
	public NickUnavailableException(String msg) {
		super(msg);
	}
}
