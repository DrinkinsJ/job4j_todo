package com.job4j.todo.services;

import com.job4j.todo.model.Task;

import java.util.Collection;
import java.util.Optional;

public interface TaskService {
    Task create(Task task);

    boolean update(Task task);

    boolean deleteById(int id);

    Collection<Task> findAll();

    Collection<Task> findAllByDone(boolean isDone);

    Optional<Task> findById(int id);

    boolean changeStatus(int id);
}
