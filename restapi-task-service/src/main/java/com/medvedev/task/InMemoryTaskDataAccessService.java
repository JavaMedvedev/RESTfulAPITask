package com.medvedev.task;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository("inMemoryDB")
public class InMemoryTaskDataAccessService implements TaskDAO {

  private final static List<Task> DB = new ArrayList<>();

  @Override
  public List<Task> getTasks() {
    return DB;
  }

  @Override
  public UUID addTask(UUID id, Task task) {
    DB.add(new Task(id, task.getName(), task.getDescription(), task.getLastModificationDate()));
    return id;
  }

  @Override
  public Optional<Task> getTask(UUID id) {
    return DB
        .stream()
        .filter(task -> task.getId().equals(id))
        .findFirst();
  }

  @Override
  public int deleteTask(UUID id) {
    Optional<Task> taskOptional = getTask(id);
    if (!taskOptional.isPresent()) {
      return 0;
    }
    DB.remove(taskOptional.get());
    return 1;
  }

  @Override
  public int updateTask(UUID id, Task taskUpdate) {
    return getTask(id)
        .map(task -> {
          int indexOfTaskToDelete = DB.indexOf(task);
          if (indexOfTaskToDelete >= 0) {
            DB.set(indexOfTaskToDelete, new Task(id, taskUpdate.getName(),
                    taskUpdate.getDescription(), taskUpdate.getLastModificationDate()));
            return 1;
          }
          return 0;
        })
        .orElse(0);
  }
}
