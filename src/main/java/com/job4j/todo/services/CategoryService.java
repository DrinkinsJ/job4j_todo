package com.job4j.todo.services;

import com.job4j.todo.model.Category;

import java.util.Collection;
import java.util.Set;

public interface CategoryService {
    Collection<Category> findAll();

    Set<Category> findByIds(Collection<Integer> ids);
}
