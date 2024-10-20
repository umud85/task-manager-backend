package dev.akbayin.data;

import java.util.List;

import dev.akbayin.entity.Task;

public interface TaskDAO {

  List<Task> findAll();

  void save(Task task);

  void delete(Long id);
}
