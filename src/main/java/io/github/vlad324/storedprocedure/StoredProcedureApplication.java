package io.github.vlad324.storedprocedure;

import io.github.vlad324.storedprocedure.model.User;
import io.github.vlad324.storedprocedure.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
@Slf4j
public class StoredProcedureApplication implements CommandLineRunner {

    private final UserService userService;

    @Autowired
    public StoredProcedureApplication(final UserService userService) {
        this.userService = userService;
    }

    public static void main(String[] args) {
        SpringApplication.run(StoredProcedureApplication.class, args);
    }

    @Override
    public void run(final String... strings) {
        List<User> users = userService.getUsers("Ulad");

        users.forEach(u -> log.info(u.toString()));
    }
}
