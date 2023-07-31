package com.job4j.todo.services;

import com.job4j.todo.model.Priority;
import com.job4j.todo.repository.PriorityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@AllArgsConstructor
public class SimplePriorityService implements PriorityService {

    private final PriorityRepository priorityRepository;

    @Override
    public Collection<Priority> findAll() {
        return priorityRepository.findAll();
    }
}
