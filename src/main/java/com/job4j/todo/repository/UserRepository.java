package com.job4j.todo.repository;

import com.job4j.todo.model.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> save(User user);

    Optional<User> findByLoginAndPassword(String login, String password);

    Optional<User> findById(int id);
}
