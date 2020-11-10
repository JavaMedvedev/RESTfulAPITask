package com.medvedev.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskService {

  private final TaskDAO taskDAO;

  @Autowired
  public TaskService(@Qualifier("inMemoryDB") TaskDAO taskDAO) {
    this.taskDAO = taskDAO;
  }

  List<Task> getAllTasks() {
    return taskDAO.getTasks();
  }

  UUID insertNewTask(Task task) {
    return taskDAO.addTask(task);
  }

  Optional<Task> getTaskById(UUID taskId) {
    return taskDAO.getTask(taskId);
  }

  void deleteTask(UUID taskId) {
    taskDAO.deleteTask(taskId);
  }

  void updateTask(UUID taskId, Task task) {
    taskDAO.updateTask(taskId, task);
  }
}
