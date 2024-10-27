package dev.akbayin.data;

import java.util.List;

import dev.akbayin.entity.Task;

public interface TaskDao {
  Task findById(Long id);

  List<Task> findAll();

  Task save(Task task);

  void update(Task task);

  void delete(Long id);
}
