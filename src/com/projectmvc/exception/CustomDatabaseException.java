package com.projectmvc.exception;

public final class CustomDatabaseException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CustomDatabaseException(final String message) {
        super(message);
    }

    public CustomDatabaseException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
