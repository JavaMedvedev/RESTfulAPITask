package com.medvedev.task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskDAO {

  UUID addTask(UUID id, Task task);

  default UUID addTask(Task task) {
    UUID id = UUID.randomUUID();
    return addTask(id, task);
  }

  List<Task> getTasks();

  Optional<Task> getTask(UUID id);

  int deleteTask(UUID id);

  int updateTask(UUID id, Task task);

}
