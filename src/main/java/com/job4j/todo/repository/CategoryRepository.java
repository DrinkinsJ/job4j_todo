package com.job4j.todo.repository;

import com.job4j.todo.model.Category;

import java.util.Collection;
import java.util.Set;

public interface CategoryRepository {
    Collection<Category> findAll();

    Set<Category> findByIds(Collection<Integer> ids);
}
