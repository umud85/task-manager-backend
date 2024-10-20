package dev.akbayin.dto;

import dev.akbayin.entity.Task;

public record TaskDto(boolean isDone, String description) {

  public TaskDto(Task task) {
    this(task.isDone(), task.getDescription());
  }
}
