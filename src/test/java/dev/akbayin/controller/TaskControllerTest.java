package dev.akbayin.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

import java.util.List;

import dev.akbayin.model.Task;
import dev.akbayin.service.TaskService;
import dev.akbayin.dto.*;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TaskService taskService;

  @Test
  void createTask_ShouldReturnCreatedTask() throws Exception {
    Task task = new Task(1L, false, "Test Task");
    TaskRequest taskRequest = new TaskRequest("Test Task");

    when(taskService.createTask(any(TaskRequest.class))).thenReturn(task);

    mockMvc.perform(post("/api/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"description\": \"Test Task\"}"))
        .andExpect(status().isCreated())
        .andExpect(header().string("Location", "/api/tasks/1"))
        .andExpect(jsonPath("$.description").value("Test Task"));
  }
  

  @Test
  void createTask_ShouldReturnBadRequest_WhenTaskIsNull() throws Exception {
    when(taskService.createTask(any(TaskRequest.class))).thenReturn(null);

    mockMvc.perform(post("/api/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"description\": \"\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void fetchAllTasks_ShouldReturnAllTasks() throws Exception {
      // Arrange
      List<Task> mockTasks = List.of(
          new Task(1L, false, "Buy groceries"),
          new Task(2L, false, "Complete homework")
      );

      when(taskService.getAllTasks()).thenReturn(mockTasks);

      // Act & Assert
      mockMvc.perform(get("/api/tasks"))
              .andExpect(status().isOk())
              .andExpect(content().contentType(MediaType.APPLICATION_JSON))
              .andExpect(jsonPath("$", hasSize(2))) // Verify the size of the returned list
              .andExpect(jsonPath("$[0].id").value(1L)) // Verify properties of the first task
              .andExpect(jsonPath("$[0].description").value("Buy groceries"))
              .andExpect(jsonPath("$[0].done").value(false))
              .andExpect(jsonPath("$[1].id").value(2L)) // Verify properties of the second task
              .andExpect(jsonPath("$[1].description").value("Complete homework"))
              .andExpect(jsonPath("$[1].done").value(false));
  }
}
