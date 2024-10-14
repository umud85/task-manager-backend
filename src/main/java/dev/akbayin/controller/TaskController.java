package dev.akbayin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.akbayin.service.TaskService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import dev.akbayin.dto.TaskRequest;
import dev.akbayin.model.Task;

import java.net.*;
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
  public ResponseEntity<Task> createTask(@RequestBody TaskRequest taskRequest) {
    Task createdTask = taskService.createTask(taskRequest);

    if (createdTask == null) {
      return ResponseEntity.badRequest().build();
    }

    return ResponseEntity.created(URI.create("/api/tasks/" + createdTask.getId()))
        .body(createdTask);
  }
  
  @CrossOrigin
  @GetMapping()
  public ResponseEntity<List<Task>> getAllTasks() {
    List<Task> tasks = taskService.getAllTasks();

    if (tasks == null) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    if (tasks.isEmpty()) {
      return ResponseEntity.noContent().build();
    }

    return ResponseEntity.ok(tasks);
  }

}
