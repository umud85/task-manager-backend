package dev.akbayin.service;

import org.springframework.stereotype.Service;

import dev.akbayin.dto.TaskDto;
import dev.akbayin.entity.Task;
import dev.akbayin.exceptions.TaskDaoException;
import lombok.extern.slf4j.Slf4j;
import dev.akbayin.data.TaskDao;

import java.util.*;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {

  private final TaskDao taskDao;

  public TaskServiceImpl(TaskDao taskDao) {
    this.taskDao = taskDao;
  }

  public Optional<List<TaskDto>> getAllTasks() {
    try {
      List<Task> tasks = this.taskDao.findAll();
      return Optional.of(tasks.stream().map(TaskDto::new).toList());
    } catch (TaskDaoException e) {
      log.error("Error retrieving tasks from the database: {}", e.getMessage(), e);
      return Optional.empty();
    }
  }

  @Override
  public Optional<TaskDto> getTaskById(Long id) {
    try {
      Task task = this.taskDao.findById(id);
      TaskDto taskDto = new TaskDto(task.getId(), task.isDone(), task.getDescription());
      return Optional.of(taskDto);
    } catch (TaskDaoException e) {
      log.error("Error retrieving task with id={}. Error message: {}", id, e.getMessage(), e);
      return Optional.empty();
    }
  }

  @Override
  public Optional<Task> createTask(TaskDto taskDTO) {
    Task task = new Task(taskDTO.isDone(), taskDTO.description());
    try {
      Task createdTask = taskDao.save(task);
      return Optional.of(createdTask);
    } catch (TaskDaoException e) {
      log.error("Error creating task: {}", e.getMessage(), e);
      return Optional.empty();
    }
  }

  @Override
  public Optional<Task> updateTask(TaskDto taskDto, Long id) {
    Task task = new Task(taskDto.isDone(), taskDto.description());
    task.setId(id);
    try {
      taskDao.update(task);
      return Optional.of(task);
    } catch (TaskDaoException e) {
      log.error("Error updating task with id={}. Error message: {}", taskDto.id(), e.getMessage(), e);
      return Optional.empty();
    }
  }

  @Override
  public void deleteTask(Long id) {
    try {
      taskDao.delete(id);
    } catch (TaskDaoException e) {
      log.error("Error deleting task with id={}. Error message: {}", id, e.getMessage(), e);
    }
  }
}
