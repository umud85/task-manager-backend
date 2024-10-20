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

import dev.akbayin.service.TaskService;
import dev.akbayin.dto.TaskDto;
import dev.akbayin.entity.Task;

@WebMvcTest(TaskController.class)
public class TaskControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TaskService taskService;

  @Test
  void createTask_ShouldReturnCreatedTask() throws Exception {
    Task task = new Task(false, "Test Task");

    when(taskService.saveTask(any(TaskDto.class))).thenReturn(task);

    mockMvc.perform(post("/api/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"description\": \"Test Task\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.description").value("Test Task"));
  }

  @Test
  void createTask_ShouldReturnBadRequest_WhenTaskIsNull() throws Exception {
    when(taskService.saveTask(any(TaskDto.class))).thenReturn(null);

    mockMvc.perform(post("/api/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"description\": \"\"}"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void fetchAllTasks_ShouldReturnAllTasks() throws Exception {
    // Arrange
    List<TaskDto> mockTasks = List.of(
        new TaskDto(false, "Buy groceries"),
        new TaskDto(false, "Complete homework"));

    when(taskService.getAllTasks()).thenReturn(mockTasks);

    // Act & Assert
    mockMvc.perform(get("/api/tasks"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2))) // Verify the size of the returned list
        .andExpect(jsonPath("$[0].description").value("Buy groceries"))
        .andExpect(jsonPath("$[0].isDone").value(false))
        .andExpect(jsonPath("$[1].description").value("Complete homework"))
        .andExpect(jsonPath("$[1].isDone").value(false));
  }
}
