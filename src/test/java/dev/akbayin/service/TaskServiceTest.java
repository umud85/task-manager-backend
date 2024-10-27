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
  private SimpleTaskService taskService;

  @Test
  void testCreateTask() {
    // Arrange: Create a TaskDTO object
    TaskDto taskDTO = new TaskDto(1L, false, "Sample Task");

    // Act: Call the saveTask method of the service
    Task savedTask = taskService.createTask(taskDTO);

    // Assert: Verify taskDAO.save() was called
    verify(taskDao).save(any(Task.class));

    // Verify taskDAO.save() was called with the expected Task object
    ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
    verify(taskDao).save(taskCaptor.capture());
    Task capturedTask = taskCaptor.getValue();

    assertEquals("Sample Task", capturedTask.getDescription());
    assertFalse(capturedTask.isDone());

    // Assert the properties of the returned task
    assertEquals("Sample Task", savedTask.getDescription());
    assertFalse(savedTask.isDone());
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
    assertEquals(true, taskDtos.get().get(0).isDone());
    assertEquals("Test First", taskDtos.get().get(0).description());
    assertEquals(false, taskDtos.get().get(1).isDone());
    assertEquals("Then implement", taskDtos.get().get(1).description());
  }

  @Test
  void testGetAllTasks_EmptyList() {
    // Arrange: Mock taskDAO to return an empty list
    when(taskDao.findAll()).thenReturn(Collections.emptyList());

    // Act: Call the method under test
    Optional<List<TaskDto>> taskDTOs = taskService.getAllTasks();

    // Assert: Verify that the returned list is empty
    assertEquals(0, taskDTOs.get().size());
  }

  @Test
  void testGetTaskById() {
    Task task = new Task(false, "Finish backend");

    when(taskDao.findById(1L)).thenReturn(task);
    Optional<TaskDto> taskDto = taskService.getTaskById(1L);

    assertTrue(taskDto.isPresent());
    assertEquals(false, taskDto.get().isDone());
    assertEquals("Finish backend", taskDto.get().description());
  }

  @Test
  void shouldUpdateTaskWithValidId() {
    Task task = new Task(false, "Finish backend");
    task.setId(1L);

  }
}
