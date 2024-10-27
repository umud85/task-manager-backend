package dev.akbayin.dto;

import dev.akbayin.entity.Task;

public record TaskDto(Long id, boolean isDone, String description) {

  public TaskDto(Task task) {
    this(task.getId(), task.isDone(), task.getDescription());
  }
}
