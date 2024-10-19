package dev.akbayin.service;

import java.util.List;

import dev.akbayin.dto.TaskDTO;
import dev.akbayin.entity.Task;

public interface TaskService {

  List<TaskDTO> getAllTasks();

  TaskDTO getTaskById(Long id);

  Task saveTask(TaskDTO taskDTO);

  void deleteTask(Long Id);
}
