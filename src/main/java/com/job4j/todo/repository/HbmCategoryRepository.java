package com.job4j.todo.repository;

import com.job4j.todo.model.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@AllArgsConstructor
public class HbmCategoryRepository implements CategoryRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        return crudRepository.query("FROM Category", Category.class);
    }

    @Override
    public Set<Category> findByIds(Collection<Integer> ids) {
        var categories =  crudRepository.query("FROM Category Where id IN :listId", Category.class, Map.of("listId", ids));
        return new HashSet<>(categories);
    }
}
