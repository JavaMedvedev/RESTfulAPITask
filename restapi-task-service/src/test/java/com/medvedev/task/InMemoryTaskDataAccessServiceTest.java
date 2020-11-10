package com.medvedev.task;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class InMemoryTaskDataAccessServiceTest {

  private InMemoryTaskDataAccessService underTest;

  @Before
  public void setUp() {
    underTest = new InMemoryTaskDataAccessService();
  }

  @Test
  public void canPerformCrud() {
    // Given taskOne has name: "Shipping", description: "To ship..."
    UUID idOne = UUID.randomUUID();
    Task taskOne = new Task(idOne, "Shipping", "To ship...", LocalDateTime.now());

    // ...And taskTwo has name: "Dispatching", description: "To dispatch..."
    UUID idTwo = UUID.randomUUID();
    Task taskTwo = new Task(idTwo, "Dispatching", "To dispatch...", LocalDateTime.now());

    // When taskOne and taskTwo added to db
    underTest.addTask(idOne, taskOne);
    underTest.addTask(idTwo, taskTwo);

    // Then can retrieve taskOne by id
    assertThat(underTest.getTask(idOne))
        .isPresent()
        .hasValueSatisfying(taskFromDb -> assertThat(taskFromDb).isEqualToComparingFieldByField(taskOne));

    // ...And also taskTwo by id
    assertThat(underTest.getTask(idTwo))
        .isPresent()
        .hasValueSatisfying(taskFromDb -> assertThat(taskFromDb).isEqualToComparingFieldByField(taskTwo));

    // When get all tasks
    List<Task> tasks = underTest.getTasks();

    // ...List should have size 2 and should have both taskOne and taskTwo
    assertThat(tasks)
        .hasSize(2)
        .usingFieldByFieldElementComparator()
        .containsExactlyInAnyOrder(taskOne, taskTwo);

    // ... An update request ("Shipping" name to "Delivering")
    Task taskUpdate = new Task(idOne, "Delivering", "To deliver...", LocalDateTime.now());

    // When Update
    assertThat(underTest.updateTask(idOne, taskUpdate)).isEqualTo(1);

    // Then when get task with idOne then should have name as Shipping > Delivering
    assertThat(underTest.getTask(idOne))
        .isPresent()
        .hasValueSatisfying(taskFromDb -> assertThat(taskFromDb).isEqualToComparingFieldByField(taskUpdate));

    // When Delete taskOne
    assertThat(underTest.deleteTask(idOne)).isEqualTo(1);

    // When get taskOne should be empty
    assertThat(underTest.getTask(idOne)).isEmpty();

    // DB should only contain taskTwo
    assertThat(underTest.getTasks())
        .hasSize(1)
        .usingFieldByFieldElementComparator()
        .containsExactlyInAnyOrder(taskTwo);
  }

  @Test
  public void willReturn0IfNoTaskFoundToDelete() {
    // Given
    UUID id = UUID.randomUUID();

    // When
    int deleteResult = underTest.deleteTask(id);

    // Then
    assertThat(deleteResult).isEqualTo(0);
  }

  @Test
  public void willReturn0IfNoTaskFoundToUpdate() {
    // Given
    UUID id = UUID.randomUUID();
    Task task = new Task(id, "Unknown Task", "There is no such a task in the DB...", LocalDateTime.now());

    // When
    int deleteResult = underTest.updateTask(id, task);

    // Then
    assertThat(deleteResult).isEqualTo(0);
  }
}