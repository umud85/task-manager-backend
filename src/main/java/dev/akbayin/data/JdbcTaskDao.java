package dev.akbayin.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import dev.akbayin.entity.Task;
import dev.akbayin.exceptions.TaskDaoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class JdbcTaskDao implements TaskDao {

  private final DataSource dataSource;

  private static final String INSERT_TASK_SQL = "INSERT INTO Task (is_done, description) VALUES (?, ?)";
  private static final String UPDATE_TASK_SQL = "UPDATE Task SET is_done = ?, description = ? WHERE id = ?;";
  private static final String GET_TASKS_SQL = "SELECT * FROM Task";
  private static final String GET_TASK_BY_ID = "SELECT * FROM Task WHERE ID=?";
  private static final String DELETE_TASK_BY_ID = "DELETE FROM Task WHERE ID = ?";

  public JdbcTaskDao(DataSource dataSource) {
      this.dataSource = dataSource;
  }

    @Override
  public List<Task> findAll() {
    List<Task> tasks = new ArrayList<>();
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_TASKS_SQL)) {

      statement.execute();
      ResultSet resultSet = statement.getResultSet();

      while (resultSet.next()) {
        Task task = new Task();
        task.setId(resultSet.getLong("id"));
        task.setDone(resultSet.getBoolean("is_done"));
        task.setDescription(resultSet.getString("description"));
        tasks.add(task);
      }
    } catch (SQLException e) {
      log.error("Failed to retrieve tasks: " + e);
      throw new TaskDaoException("Error retrieving tasks " + e);
    }
    return tasks;
  }

  @Override
  public Task findById(Long id) {
    Task task;
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_TASK_BY_ID)) {

      statement.setLong(1, id);
      statement.execute();

      ResultSet resultSet = statement.getResultSet();

      resultSet.next();

      task = new Task();
      task.setId(resultSet.getLong("id"));
      task.setDone(resultSet.getBoolean("is_done"));
      task.setDescription(resultSet.getString("description"));

    } catch (SQLException e) {
      log.error("Failed to retrieve tasks: " + e);
      throw new TaskDaoException("Error retrieving task " + e);
    }
    return task;
  }

  @Override
  public Task save(Task task) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_TASK_SQL, Statement.RETURN_GENERATED_KEYS)) {

      statement.setInt(1, task.isDone() ? 1 : 0);
      statement.setString(2, task.getDescription());

      int affectedRows = statement.executeUpdate();

      // Check if the insert was successful
      if (affectedRows > 0) {
        // Retrieve generated keys
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
          if (generatedKeys.next()) {
            // Populate the task with the generated ID
            task.setId(generatedKeys.getLong(1));
          } else {
            throw new SQLException("Creating task failed, no ID obtained.");
          }
        }
      }
      return task;
    } catch (SQLException e) {
      log.error("Failed to create the Task: " + e.getMessage());
      throw new TaskDaoException("Error saving task", e);
    }
  }
  
  @Override
  public void update(Task task) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(UPDATE_TASK_SQL)) {

      statement.setInt(1, task.isDone() ? 1 : 0);
      statement.setString(2, task.getDescription());
      statement.setLong(3, task.getId());
      int rowsAffected = statement.executeUpdate();

      if (rowsAffected == 0) {
        throw new SQLException("No rows affected, task not updated.");
      }
    } catch (SQLException e) {
      log.error("Failed to update the Task: " + e.getMessage());
      throw new TaskDaoException("Error updating task", e);
    }
  }

  @Override
  public void delete(Long id) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(DELETE_TASK_BY_ID)) {
      
      statement.setLong(1, id);
      int rowsAffected = statement.executeUpdate();
      if (rowsAffected == 0) {
        throw new SQLException("No rows affected, task not deleted.");
      }

    } catch (SQLException e) {
      log.error("Failed to delete the Task: " + e.getMessage());
      throw new TaskDaoException("Error deleting task", e);
    }
  }
}
