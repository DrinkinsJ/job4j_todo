package com.job4j.todo.repository;

import com.job4j.todo.model.Category;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Repository
@AllArgsConstructor
public class HbmCategoryRepository implements CategoryRepository {

    private final CrudRepository crudRepository;

    @Override
    public Collection<Category> findAll() {
        return crudRepository.query("FROM Category", Category.class);
    }

    @Override
    public Collection<Category> findByIds(Collection<Integer> ids) {
        return crudRepository.query("FROM Category Where id IN :listId", Category.class, Map.of("listId", ids));
    }
}
