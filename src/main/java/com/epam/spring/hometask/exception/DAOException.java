package com.epam.spring.hometask.exception;

/**
 * Created by Hanna_Tsykunova on 1/17/2018.
 */
public class DAOException extends Exception {
  public DAOException() {
  }

  public DAOException(String message) {
    super(message);
  }

  public DAOException(String message, Throwable cause) {
    super(message, cause);
  }

  public DAOException(Throwable cause) {
    super(cause);
  }

  public DAOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
