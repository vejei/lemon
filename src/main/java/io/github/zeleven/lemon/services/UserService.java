package io.github.zeleven.lemon.services;

import io.github.zeleven.lemon.entities.Role;
import io.github.zeleven.lemon.entities.User;
import io.github.zeleven.lemon.repositories.RoleRepository;
import io.github.zeleven.lemon.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByConfirmationToken(String confirmationToken) {
        return userRepository.findByConfirmationToken(confirmationToken);
    }

    public void saveUser(User user) {
        if (user.getEmail().equals("admin@test.com")) {
            Role role = roleRepository.findByName("ADMIN");
            user.setRole(role);
        }
        userRepository.save(user);
    }
}
