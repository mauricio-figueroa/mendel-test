package com.mendel.transaction.exception;

import static javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST;
import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

public final class TransactionNotFoundException extends BusinessException {

  private static final long serialVersionUID = -32L;

  private static final String CREATION_ERROR_MESSAGE = "Transaction doesn't exist";

  private TransactionNotFoundException(
      final String message, final int statusCode, final Throwable cause) {
    super(message, statusCode, cause);
  }

  public static TransactionNotFoundException create(final Throwable cause) {
    return new TransactionNotFoundException(CREATION_ERROR_MESSAGE, SC_BAD_REQUEST, cause);
  }

  public static TransactionNotFoundException create() {
    return new TransactionNotFoundException(CREATION_ERROR_MESSAGE, SC_BAD_REQUEST, null);
  }
}
