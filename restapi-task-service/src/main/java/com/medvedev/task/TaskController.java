package com.medvedev.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/api")
public class TaskController {

  private final TaskService taskService;

  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping("/task")
  public List<Task> getAll() {
    return taskService.getAllTasks();
  }

  @GetMapping("/task/{id}")
  public Task getTask(@NotNull @PathVariable("id") UUID id) {
    return taskService.getTaskById(id)
        .orElse(null);
  }

  @PostMapping("/task")
  public UUID createNewTask(@NotNull @Valid @RequestBody Task task) {
    return taskService.insertNewTask(task);
  }

  @DeleteMapping("/task/{id}")
  public void deleteTask(@NotNull @PathVariable("id") UUID id) {
    taskService.deleteTask(id);
  }

  @PutMapping("/task/{id}")
  public void deleteTask(@NotNull @PathVariable("id") UUID id, @NotNull @Valid @RequestBody Task task) {
    taskService.updateTask(id, task);
  }
}
