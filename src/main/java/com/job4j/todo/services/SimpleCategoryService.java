package com.job4j.todo.services;

import com.job4j.todo.model.Category;
import com.job4j.todo.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;

@Service
@AllArgsConstructor
public class SimpleCategoryService implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Collection<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Set<Category> findByIds(Collection<Integer> ids) {
        return categoryRepository.findByIds(ids);
    }
}
