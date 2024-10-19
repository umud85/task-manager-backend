package dev.akbayin.repository;

import java.util.List;

import dev.akbayin.entity.Task;

public interface TaskRepository {

  List<Task> findAll();

  void save(Task task);

  void delete(Long id);
}
