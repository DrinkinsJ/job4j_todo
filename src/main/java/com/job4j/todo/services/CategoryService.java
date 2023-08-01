package com.job4j.todo.services;

import com.job4j.todo.model.Category;

import java.util.Collection;
import java.util.List;

public interface CategoryService {
    Collection<Category> findAll();

    Collection<Category> findByIds(Collection<Integer> ids);
}
