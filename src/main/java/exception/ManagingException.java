package exception;

public class ManagingException extends RuntimeException {

	public ManagingException() {
		super();
	}

	public ManagingException(String arg0) {
		super(arg0);
	}

	public ManagingException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ManagingException(Throwable arg2) {
		super(arg2);
	}

}
