package dev.akbayin.dto;

import dev.akbayin.entity.Task;
import jakarta.validation.constraints.NotNull;

public record TaskDTO(boolean isDone, String description) {

  public TaskDTO(Task task) {
    this(task.isDone(), task.getDescription());
  }
}

