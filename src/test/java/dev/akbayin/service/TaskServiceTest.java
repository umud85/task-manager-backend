package dev.akbayin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.akbayin.data.TaskDao;
import dev.akbayin.dto.TaskDto;
import dev.akbayin.entity.Task;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

  @Mock
  private TaskDao taskDao;

  @InjectMocks
  private TaskServiceImpl taskService;

  @Test
  void testCreateTask() {
    TaskDto taskDto = new TaskDto(1L, false, "Sample Task");

    Task task = new Task(false, "Sample Task");
    task.setId(1L);
    when(taskDao.save(any(Task.class))).thenReturn(task);

    Optional<Task> savedTask = taskService.createTask(taskDto);

    verify(taskDao).save(any(Task.class));

    ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
    verify(taskDao).save(taskCaptor.capture());
    Task capturedTask = taskCaptor.getValue();

    assertEquals("Sample Task", capturedTask.getDescription());
    assertFalse(capturedTask.isDone());

    assertTrue(savedTask.isPresent());
    assertEquals("Sample Task", savedTask.get().getDescription());
    assertFalse(savedTask.get().isDone());
  }

  @Test
  void testGetAllTasks() {
    Task taskWithTrue = new Task(true, "Test First");
    Task taskWithFalse = new Task(false, "Then implement");
    List<Task> tasks = List.of(taskWithTrue, taskWithFalse);

    when(taskDao.findAll()).thenReturn(tasks);
    Optional<List<TaskDto>> taskDtos = taskService.getAllTasks();

    assertTrue(taskDtos.isPresent());
    assertEquals(2, taskDtos.get().size());
    assertTrue(taskDtos.get().get(0).isDone());
    assertEquals("Test First", taskDtos.get().get(0).description());
    assertFalse(taskDtos.get().get(1).isDone());
    assertEquals("Then implement", taskDtos.get().get(1).description());
  }

  @Test
  void testGetAllTasks_EmptyList() {
    // Arrange: Mock taskDAO to return an empty list
    when(taskDao.findAll()).thenReturn(Collections.emptyList());

    // Act: Call the method under test
    Optional<List<TaskDto>> taskDtos = taskService.getAllTasks();

    // Assert: Verify that the returned list is empty
    assertTrue(taskDtos.isPresent());
    assertEquals(0, taskDtos.get().size());
  }

  @Test
  void testGetTaskById() {
    Task task = new Task(false, "Finish backend");

    when(taskDao.findById(1L)).thenReturn(task);
    Optional<TaskDto> taskDto = taskService.getTaskById(1L);

    assertTrue(taskDto.isPresent());
    assertFalse(taskDto.get().isDone());
    assertEquals("Finish backend", taskDto.get().description());
  }

  @Test
  void shouldUpdateTaskWithValidId() {
    Task task = new Task(false, "Sample Task");
    task.setId(1L);

    when(taskDao.findById(1L)).thenReturn(task);

    Optional<TaskDto> taskDtoOptional = taskService.getTaskById(1L);

    assertTrue(taskDtoOptional.isPresent());
    assertFalse(taskDtoOptional.get().isDone());
    assertEquals("Sample Task", taskDtoOptional.get().description());

    TaskDto taskDto = new TaskDto(1L, true, "Sample Task");

    Optional<Task> updatedTask = taskService.updateTask(taskDto, taskDto.id());

    verify(taskDao).update(any(Task.class));

    ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
    verify(taskDao).update(taskCaptor.capture());
    Task capturedTask = taskCaptor.getValue();

    assertEquals("Sample Task", capturedTask.getDescription());
    assertTrue(capturedTask.isDone());

    assertEquals("Sample Task", updatedTask.get().getDescription());
    assertTrue(updatedTask.get().isDone());
  }

  @Test
  void shouldDeleteTaskWithValidId() {
    Task task = new Task(false, "Sample Task");
    task.setId(1L);

    when(taskDao.findById(1L)).thenReturn(task);

    Optional<TaskDto> taskDtoOptional = taskService.getTaskById(1L);
    assertTrue(taskDtoOptional.isPresent());

    taskService.deleteTask(task.getId());

    verify(taskDao).delete(task.getId());
  }
}
