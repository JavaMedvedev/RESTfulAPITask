package com.medvedev.task;

import java.time.LocalDateTime;
import java.util.UUID;

public class Task {

  private final UUID id;

  private final String name;

  private final String description;

  private final LocalDateTime lastModificationDate = LocalDateTime.now();

  public Task(UUID id, String name, String description, LocalDateTime lastModificationDate) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public LocalDateTime getLastModificationDate() {
    return lastModificationDate;
  }
}
