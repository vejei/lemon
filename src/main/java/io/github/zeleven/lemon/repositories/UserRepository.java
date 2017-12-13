package io.github.zeleven.lemon.repositories;

import io.github.zeleven.lemon.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String email);
    User findByConfirmationToken(String findByConfirmationToken);
}
