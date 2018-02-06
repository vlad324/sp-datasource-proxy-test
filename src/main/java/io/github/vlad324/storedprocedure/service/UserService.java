package io.github.vlad324.storedprocedure.service;

import io.github.vlad324.storedprocedure.model.User;

import java.util.List;

/**
 * @author v.radkevich
 * @since 2/5/18
 */
public interface UserService {
    List<User> getUsers(String name);
}
