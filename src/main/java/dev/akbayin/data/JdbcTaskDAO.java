package dev.akbayin.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dev.akbayin.entity.Task;
import dev.akbayin.exceptions.TaskDaoException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class JdbcTaskDao implements TaskDao {

  @Autowired
  private DataSource dataSource;

  private static final String INSERT_TASK_SQL = "INSERT INTO Task (is_done, description) VALUES (?, ?)";
  private static final String GET_TASKS_SQL = "SELECT * FROM Task";

  @Override
  public List<Task> findAll() {
    List<Task> tasks = new ArrayList<>();
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(GET_TASKS_SQL)) {

      statement.execute();
      ResultSet resultSet = statement.getResultSet();

      while (resultSet.next()) {
        Task task = new Task();
        task.setDone(resultSet.getBoolean("is_done"));
        task.setDescription(resultSet.getString("description"));
        tasks.add(task);
      }
    } catch (SQLException e) {
      log.error("Failed to retrieve tasks: " + e);
      return Collections.emptyList();
    }
    return tasks;
  }

  @Override
  public void save(Task task) {
    try (Connection connection = dataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(INSERT_TASK_SQL)) {

      statement.setInt(1, task.isDone() ? 1 : 0);
      statement.setString(2, task.getDescription());
      int rowsAffected = statement.executeUpdate();

      if (rowsAffected == 0) {
        throw new SQLException("No rows affected, task not inserted.");
      }
    } catch (SQLException e) {
      log.error("Failed to create the Task: " + e.getMessage());
      throw new TaskDaoException("Error saving task", e);
    }
  }

  @Override
  public void delete(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }
}
