package org.launchcode.codingevents.data;

import org.launchcode.codingevents.models.Role;
import org.launchcode.codingevents.models.RoleName;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}