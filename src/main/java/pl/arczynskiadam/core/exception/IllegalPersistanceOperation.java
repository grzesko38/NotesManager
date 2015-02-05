package pl.arczynskiadam.core.exception;

public class IllegalPersistanceOperation extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public IllegalPersistanceOperation(String msg) {
		super(msg);
	}
}
