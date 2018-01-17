package exceptions;

public class PayeTaSoireeRuntimeException extends RuntimeException {

	private static final long serialVersionUID = 9025730660535871559L;

	public PayeTaSoireeRuntimeException() {
		super();
	}

	public PayeTaSoireeRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public PayeTaSoireeRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public PayeTaSoireeRuntimeException(String message) {
		super(message);
	}

	public PayeTaSoireeRuntimeException(Throwable cause) {
		super(cause);
	}
	
	

}
