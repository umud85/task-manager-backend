package dev.akbayin.service;

import org.springframework.stereotype.Service;

import dev.akbayin.dto.TaskDto;
import dev.akbayin.entity.Task;
import dev.akbayin.data.TaskDao;

import java.util.*;

@Service
public class SimpleTaskService implements TaskService {

  private final TaskDao taskDAO;

  public SimpleTaskService(TaskDao taskDAO) {
    this.taskDAO = taskDAO;
  }

  public List<TaskDto> getAllTasks() {
    List<Task> tasks = this.taskDAO.findAll();
    List<TaskDto> taskDTOs = tasks.stream().map(TaskDto::new).toList();
    return taskDTOs;
  }

  @Override
  public TaskDto getTaskById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTaskById'");
  }

  @Override
  public Task saveTask(TaskDto taskDTO) {
    Task task = new Task(taskDTO.isDone(), taskDTO.description());
    taskDAO.save(task);
    return task;
  }

  @Override
  public void deleteTask(Long Id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteTask'");
  }
}
