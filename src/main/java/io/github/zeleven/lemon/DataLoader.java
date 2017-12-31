package io.github.zeleven.lemon;

import io.github.zeleven.lemon.entities.User;
import io.github.zeleven.lemon.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User userExists = userService.findByEmail("admin@test.com");
        if (userExists == null) {
            User user = new User();
            user.setName("admin");
            user.setEmail("admin@test.com");
            user.setPassword(bCryptPasswordEncoder.encode("123456"));
            user.setActive(1);
            userService.saveUser(user);
        }
    }
}
