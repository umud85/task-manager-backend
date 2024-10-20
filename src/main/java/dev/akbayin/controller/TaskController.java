package dev.akbayin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.akbayin.service.TaskService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dev.akbayin.dto.TaskDto;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @CrossOrigin
  @PostMapping
  public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDTO) {
    try {
      taskService.saveTask(taskDTO);
      if (taskDTO.description().isEmpty()) {
        return ResponseEntity.badRequest().build();
      }
      return ResponseEntity.status(HttpStatus.CREATED).body(taskDTO);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @CrossOrigin
  @GetMapping()
  public ResponseEntity<List<TaskDto>> getAllTasks() {
    List<TaskDto> tasks = taskService.getAllTasks();

    if (tasks == null) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    if (tasks.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(tasks);
  }

}
