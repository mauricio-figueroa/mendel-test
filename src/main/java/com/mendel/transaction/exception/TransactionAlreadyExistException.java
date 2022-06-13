package com.mendel.transaction.exception;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

public final class TransactionAlreadyExistException extends BusinessException {

    private static final long serialVersionUID = -32L;

    private static final String CREATION_ERROR_MESSAGE = "The transaction you are trying to create already exists";

    private TransactionAlreadyExistException(
            final String message, final int statusCode, final Throwable cause) {
        super(message, statusCode, cause);
    }

    public static TransactionAlreadyExistException create(final Throwable cause) {
        return new TransactionAlreadyExistException(CREATION_ERROR_MESSAGE, SC_BAD_REQUEST, cause);
    }

    public static TransactionAlreadyExistException create() {
        return new TransactionAlreadyExistException(CREATION_ERROR_MESSAGE, SC_BAD_REQUEST, null);
    }
}
