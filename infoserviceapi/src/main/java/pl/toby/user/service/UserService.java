package pl.toby.user.service;


import pl.toby.user.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<User> findAll();
    User findById(UUID id);
    User findByUsername(String username);
}
