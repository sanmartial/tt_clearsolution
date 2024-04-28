package org.globaroman.clearsolution.repository.impl;

import org.globaroman.clearsolution.exception.EntityNotFoundCustomException;
import org.globaroman.clearsolution.model.User;
import org.globaroman.clearsolution.repository.UserRepository;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class UserRepositoryImpl implements UserRepository {
    private final List<User> users = new ArrayList<>();

    @Override
    public User save(User user) {
        users.add(user);
        return users.get(users.size() - 1);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findById(Long id) {
        for (User user : users) {
            if (Objects.equals(user.getId(), id)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public boolean deleteById(Long id) {
        User user = findById(id)
                .orElseThrow(() -> new EntityNotFoundCustomException(
                        "There is no user by id: " + id));
        return users.remove(user);
    }
}
