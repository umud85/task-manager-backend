package dev.akbayin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.akbayin.service.TaskService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dev.akbayin.dto.TaskDto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
      if (taskDTO.description().isEmpty()) {
        return ResponseEntity.badRequest().build();
      }
      taskService.createTask(taskDTO);
      return ResponseEntity.status(HttpStatus.CREATED).body(taskDTO);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @CrossOrigin
  @GetMapping
  public ResponseEntity<List<TaskDto>> getAllTasks() {
    Optional<List<TaskDto>> tasks = taskService.getAllTasks();

    if (tasks.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(tasks.get());
  }

  @CrossOrigin
  @GetMapping("/{taskId}")
  public ResponseEntity<TaskDto> getTaskById(@PathVariable Long taskId) {
    Optional<TaskDto> taskDto = taskService.getTaskById(taskId);

    if (taskDto.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(taskDto.get());
  }

  @CrossOrigin
  @PutMapping("/{taskId}")
  public ResponseEntity<TaskDto> updateTask(@RequestBody TaskDto taskDTO) {
    try {
      if (taskDTO.description().isEmpty()) {
        return ResponseEntity.badRequest().build();
      }
      taskService.updateTask(taskDTO);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

}
