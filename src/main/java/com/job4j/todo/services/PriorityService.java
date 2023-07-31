package com.job4j.todo.services;

import com.job4j.todo.model.Priority;

import java.util.Collection;

public interface PriorityService {
    Collection<Priority> findAll();
}
