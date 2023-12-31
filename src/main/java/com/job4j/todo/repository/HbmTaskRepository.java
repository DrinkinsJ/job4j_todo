package com.job4j.todo.repository;

import com.job4j.todo.model.Task;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class HbmTaskRepository implements TaskRepository {

    private final CrudRepository crudRepository;

    @Override
    public Task create(Task task) {
        crudRepository.run(session -> session.persist(task));
        return task;
    }

    @Override
    public boolean update(Task task) {
        boolean result = false;
        try {
            crudRepository.run(session -> session.merge(task));
            result = true;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return result;
    }

    @Override
    public boolean deleteById(int id) {
        boolean result = false;
        try {
            crudRepository.run(
                    "DELETE FROM Task WHERE id = :fId",
                    Map.of("fId", id)
            );
            result = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    @Override
    public Collection<Task> findAll() {
        return crudRepository.query("SELECT DISTINCT f FROM Task f JOIN FETCH f.categories JOIN FETCH f.priority ORDER BY f.id", Task.class);
    }

    @Override
    public Collection<Task> findAllByDone(boolean done) {
        return crudRepository.query("SELECT DISTINCT f FROM Task f JOIN FETCH f.categories JOIN FETCH f.priority WHERE f.done = :done ORDER BY f.id", Task.class, Map.of("done", done));
    }

    @Override
    public Optional<Task> findById(int id) {
        return crudRepository.optional("FROM Task f JOIN FETCH f.categories JOIN FETCH f.priority WHERE f.id = :fId", Task.class, Map.of("fId", id));
    }

    @Override
    public boolean changeStatus(int id) {
        boolean result = false;
        Task task = findById(id).orElseThrow();
        try {
            crudRepository.run(
                    "UPDATE Task SET done = :done WHERE id = :fId",
                    Map.of("done", !task.isDone(), "fId", task.getId())
            );
            result = true;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }
}
