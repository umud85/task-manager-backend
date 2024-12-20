package dev.akbayin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.akbayin.service.TaskService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dev.akbayin.dto.TaskDto;
import dev.akbayin.entity.Task;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.*;


@RestController
@RequestMapping("/api/tasks")
@CrossOrigin(origins = "http://localhost:3000")
public class TaskController {

  private final TaskService taskService;

  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @PostMapping
  public ResponseEntity<TaskDto> createTask(@RequestBody TaskDto taskDto) {
    try {
      if (taskDto.description() == null || taskDto.description().isEmpty()) {
        return ResponseEntity.badRequest().body(null);
      }
        Task createdTask = taskService.createTask(taskDto).orElseThrow();
        TaskDto createdTaskDto = new TaskDto(createdTask.getId(), createdTask.isDone(), createdTask.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTaskDto);
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @GetMapping
  public ResponseEntity<List<TaskDto>> getAllTasks() {
    Optional<List<TaskDto>> tasks = taskService.getAllTasks();
      return tasks.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
  }

  @GetMapping("/{taskId}")
  public ResponseEntity<TaskDto> getTaskById(@PathVariable Long taskId) {
    Optional<TaskDto> taskDto = taskService.getTaskById(taskId);

    return taskDto.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.noContent().build());
  }

  @PutMapping("/{taskId}")
  public ResponseEntity<Void> updateTask(@RequestBody TaskDto taskDto, @PathVariable Long taskId) {
    try {
      if (taskDto.description().isEmpty()) {
        return ResponseEntity.badRequest().build();
      }
      taskService.updateTask(taskDto, taskId);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @DeleteMapping("/{taskId}")
  public ResponseEntity<Boolean> deleteTask(@PathVariable Long taskId) {
    try {
      taskService.deleteTask(taskId);
      return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    } catch (Exception e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
  }
}
