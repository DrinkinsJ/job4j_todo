package com.job4j.todo.repository;

import com.job4j.todo.model.Priority;

import java.util.Collection;

public interface PriorityRepository {

    Collection<Priority> findAll();
}
