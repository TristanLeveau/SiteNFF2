package exceptions;

public class NFFRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 9025730660535871559L;

	public NFFRuntimeException() {
		super();
	}

	public NFFRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public NFFRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NFFRuntimeException(String message) {
		super(message);
	}

	public NFFRuntimeException(Throwable cause) {
		super(cause);
	}
	
	

}
