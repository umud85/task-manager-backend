package dev.akbayin.service;

import java.util.*;

import dev.akbayin.dto.TaskDto;
import dev.akbayin.entity.Task;

public interface TaskService {

  Optional<List<TaskDto>> getAllTasks();

  Optional<TaskDto> getTaskById(Long id);

  Task createTask(TaskDto taskDTO);

  void deleteTask(Long id);
}
