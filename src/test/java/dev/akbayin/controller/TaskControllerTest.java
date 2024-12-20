package dev.akbayin.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.hasSize;

import java.util.*;

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

    given(taskService.createTask(any(TaskDto.class))).willReturn(Optional.of(task));

    mockMvc.perform(post("/api/tasks")
        .contentType(MediaType.APPLICATION_JSON)
        .content("{\"description\": \"Test Task\"}"))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.description").value("Test Task"));
  }

  @Test
  void getAllTasks_ShouldReturnNoContent_WhenTaskIsEmpty() throws Exception {
    given(taskService.getAllTasks()).willReturn(Optional.empty());

    mockMvc.perform(get("/api/tasks"))
        .andExpect(status().isNoContent())
        .andExpect(content().string(""))
        .andExpect(header().doesNotExist("Content-Type"));

    verify(taskService, times(1)).getAllTasks();
  }

  @Test
  void getAllTasks_ShouldReturnAllTasks() throws Exception {
    // Arrange
    List<TaskDto> mockTasks = List.of(
        new TaskDto(1L, false, "Buy groceries"),
        new TaskDto(2L, false, "Complete homework"));

    given(taskService.getAllTasks()).willReturn(Optional.of(mockTasks));

    // Act & Assert
    mockMvc.perform(get("/api/tasks"))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].description").value("Buy groceries"))
        .andExpect(jsonPath("$[0].isDone").value(false))
        .andExpect(jsonPath("$[1].description").value("Complete homework"))
        .andExpect(jsonPath("$[1].isDone").value(false));
  }

  @Test
  void getTaskById_ShouldReturnTask() throws Exception {
    TaskDto taskDto = new TaskDto(1L, false, "Finish backend");

    given(taskService.getTaskById(1L)).willReturn(Optional.of(taskDto));

    mockMvc.perform(get("/api/tasks/{taskId}", 1L))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.isDone").value(false))
        .andExpect(jsonPath("$.description").value("Finish backend"));
  }

  @Test
  void getTaskById_ShouldReturnNoContent_WhenDtoIsEmpty() throws Exception {
    given(taskService.getTaskById(1L)).willReturn(Optional.empty());

    mockMvc.perform(get("/api/tasks/{taskId}", 1L))
        .andExpect(status().isNoContent())
        .andExpect(content().string(""))
        .andExpect(header().doesNotExist("Content-Type"));
  }
}
