package dev.akbayin.service;

import org.springframework.stereotype.Service;

import dev.akbayin.dto.TaskDto;
import dev.akbayin.entity.Task;
import lombok.extern.slf4j.Slf4j;
import dev.akbayin.data.TaskDao;

import java.util.*;

@Slf4j
@Service
public class SimpleTaskService implements TaskService {

  private final TaskDao taskDao;

  public SimpleTaskService(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  public Optional<List<TaskDto>> getAllTasks() {
    try {
      List<Task> tasks = this.taskDao.findAll();
      return Optional.of(tasks.stream().map(TaskDto::new).toList());
    } catch (RuntimeException re) {
      log.error("Error retrieving tasks from the database: {}", re.getMessage(), re);
      return Optional.empty();
    }
  }

  @Override
  public TaskDto getTaskById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTaskById'");
  }

  @Override
  public Task saveTask(TaskDto taskDTO) {
    Task task = new Task(taskDTO.isDone(), taskDTO.description());
    taskDao.save(task);
    return task;
  }

  @Override
  public void deleteTask(Long Id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteTask'");
  }
}
