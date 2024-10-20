package dev.akbayin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import dev.akbayin.data.TaskDao;
import dev.akbayin.dto.TaskDto;
import dev.akbayin.entity.Task;

import java.util.*;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

  @Mock
  private TaskDao taskDAO;

  @InjectMocks
  private SimpleTaskService taskService;

  @Test
  void testSaveTask() {
    // Arrange: Create a TaskDTO object
    TaskDto taskDTO = new TaskDto(false, "Sample Task");

    // Act: Call the saveTask method of the service
    Task savedTask = taskService.saveTask(taskDTO);

    // Assert: Verify taskDAO.save() was called
    verify(taskDAO).save(any(Task.class));

    // Verify taskDAO.save() was called with the expected Task object
    ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
    verify(taskDAO).save(taskCaptor.capture());
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

    when(taskDAO.findAll()).thenReturn(tasks);
    Optional<List<TaskDto>> taskDTOs = taskService.getAllTasks();

    assertEquals(2, taskDTOs.get().size());
    assertEquals(true, taskDTOs.get().get(0).isDone());
    assertEquals("Test First", taskDTOs.get().get(0).description());
    assertEquals(false, taskDTOs.get().get(1).isDone());
    assertEquals("Then implement", taskDTOs.get().get(1).description());
  }

  @Test
  void testGetAllTasks_EmptyList() {
    // Arrange: Mock taskDAO to return an empty list
    when(taskDAO.findAll()).thenReturn(Collections.emptyList());

    // Act: Call the method under test
    Optional<List<TaskDto>> taskDTOs = taskService.getAllTasks();

    // Assert: Verify that the returned list is empty
    assertEquals(0, taskDTOs.get().size());
  }
}
