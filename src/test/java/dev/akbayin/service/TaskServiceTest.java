package dev.akbayin.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import dev.akbayin.data.TaskDAO;
import dev.akbayin.dto.TaskDTO;
import dev.akbayin.entity.Task;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

  @Mock
  private TaskDAO taskDAO;

  @InjectMocks
  private SimpleTaskService taskService;

  @Test
  void testSaveTask() {
      // Arrange: Create a TaskDTO object
      TaskDTO taskDTO = new TaskDTO(false, "Sample Task");

      // Act: Call the saveTask method of the service
      Task savedTask = taskService.saveTask(taskDTO);

      // Assert: Verify taskDAO.save() was called
      verify(taskDAO).save(any(Task.class));

      // Assert the properties of the returned task
      assertEquals("Sample Task", savedTask.getDescription());
      assertFalse(savedTask.isDone());
  }
}
