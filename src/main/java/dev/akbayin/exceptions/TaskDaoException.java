package dev.akbayin.exceptions;

public class TaskDaoException extends RuntimeException {

  public TaskDaoException(String message) {
    super(message);
  }

  public TaskDaoException(String message, Throwable cause) {
    super(message, cause);
  }
}
