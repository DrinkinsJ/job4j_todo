package com.job4j.todo.repository;

import com.job4j.todo.model.Priority;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
@AllArgsConstructor
public class HbmPriorityRepository implements PriorityRepository {

    private final CrudRepository crudRepository;


    @Override
    public Collection<Priority> findAll() {
        return crudRepository.query("FROM Priority", Priority.class);
    }
}
