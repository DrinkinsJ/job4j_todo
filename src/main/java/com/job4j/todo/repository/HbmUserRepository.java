package com.job4j.todo.repository;

import com.job4j.todo.model.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;

@Repository
@AllArgsConstructor
@Slf4j
public class HbmUserRepository implements UserRepository {

    private final CrudRepository crudRepository;

    @Override
    public Optional<User> save(User user) {
        Optional<User> optionalUser = Optional.empty();
        try {
            crudRepository.run(session -> session.persist(user));
            optionalUser = Optional.of(user);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return optionalUser;
    }

    @Override
    public Optional<User> findByLoginAndPassword(String login, String password) {
        return crudRepository.optional("FROM User WHERE login = :login AND password = :password", User.class,
                Map.of("login", login, "password", password));

    }

    @Override
    public Optional<User> findById(int id) {
        return crudRepository.optional("from User where id = :fId", User.class, Map.of("fId", id));
    }
}