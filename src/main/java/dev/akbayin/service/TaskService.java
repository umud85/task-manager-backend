package dev.akbayin.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.akbayin.dto.TaskRequest;
import dev.akbayin.model.Task;

import java.util.*;


@Service
public class TaskService {
  @Autowired
  private DataSource dataSource;

  private static final String INSERT_TASK_SQL = "INSERT INTO Task (isDone, description) VALUES (?, ?)";
  private static final String GET_TASKS_SQL = "SELECT * FROM Task";

  public Task createTask(TaskRequest taskRequest) {
    Task task = new Task();
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_TASK_SQL)) {

      statement.setInt(1, 0); // Default isDone to false
      statement.setString(2, taskRequest.description());
      int rowsAffected = statement.executeUpdate();

      if (rowsAffected > 0) {
        task.setDone(false);
        task.setDescription(taskRequest.description());
      } else {
        // Handle case where no rows were inserted
        return null; // or throw a custom exception
      }
    } catch (SQLException e) {
      // Use a logging framework instead of e.printStackTrace()
      throw new RuntimeException("Failed to create task", e);
    }
    return task;
  }

  public List<Task> getAllTasks() {
    List<Task> tasks = new ArrayList<>();
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_TASKS_SQL);
        ResultSet resultSet = statement.executeQuery()) {
      
      while (resultSet.next()) {
        Task task = new Task();
        task.setId(resultSet.getLong("idTask"));
        task.setDone(resultSet.getBoolean("isDone"));
        task.setDescription(resultSet.getString("description"));
        tasks.add(task);
      }
    } catch (SQLException sqle) {
      sqle.printStackTrace();
    }
    return tasks;
  }
}
