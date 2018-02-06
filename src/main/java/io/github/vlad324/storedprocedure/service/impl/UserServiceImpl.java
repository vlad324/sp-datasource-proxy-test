package io.github.vlad324.storedprocedure.service.impl;

import io.github.vlad324.storedprocedure.model.User;
import io.github.vlad324.storedprocedure.service.UserService;
import io.github.vlad324.storedprocedure.service.impl.sp.GetUserSP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author v.radkevich
 * @since 2/5/18
 */
@Service
public class UserServiceImpl implements UserService {

    private final GetUserSP sp;

    @Autowired
    public UserServiceImpl(final GetUserSP sp) {
        this.sp = sp;
    }

    @Override
    public List<User> getUsers(final String name) {
        return sp.getUsers(name);
    }
}
