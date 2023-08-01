package com.job4j.todo.repository;

import com.job4j.todo.model.Category;

import java.util.Collection;

public interface CategoryRepository {
    Collection<Category> findAll();

    Collection<Category> findByIds(Collection<Integer> ids);
}
