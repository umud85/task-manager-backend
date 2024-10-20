package dev.akbayin.service;

import java.util.List;

import dev.akbayin.dto.TaskDto;
import dev.akbayin.entity.Task;

public interface TaskService {

  List<TaskDto> getAllTasks();

  TaskDto getTaskById(Long id);

  Task saveTask(TaskDto taskDTO);

  void deleteTask(Long Id);
}
