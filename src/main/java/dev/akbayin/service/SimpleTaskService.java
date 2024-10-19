package dev.akbayin.service;

import org.springframework.stereotype.Service;

import dev.akbayin.dto.TaskDTO;
import dev.akbayin.entity.Task;
import dev.akbayin.repository.TaskRepository;

import java.util.*;

@Service
public class SimpleTaskService implements TaskService {

  private final TaskRepository taskRepository;

  public SimpleTaskService(TaskRepository taskRepository) {
    this.taskRepository = taskRepository;
  }

  public List<TaskDTO> getAllTasks() {
    List<Task> tasks = this.taskRepository.findAll();
    List<TaskDTO> taskDTOs = tasks.stream().map(TaskDTO::new).toList();
    return taskDTOs;
  }

  @Override
  public TaskDTO getTaskById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTaskById'");
  }

@Override
public Task saveTask(TaskDTO taskDTO) {
    Task task = new Task(taskDTO.isDone(), taskDTO.description());
    taskRepository.save(task);
    return task;
}


  @Override
  public void deleteTask(Long Id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'deleteTask'");
  }
}
