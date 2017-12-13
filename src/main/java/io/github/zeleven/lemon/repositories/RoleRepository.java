package io.github.zeleven.lemon.repositories;

import io.github.zeleven.lemon.entities.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findByName(String name);
}
