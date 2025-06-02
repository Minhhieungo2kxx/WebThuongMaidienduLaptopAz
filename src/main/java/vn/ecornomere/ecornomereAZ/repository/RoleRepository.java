package vn.ecornomere.ecornomereAZ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.ecornomere.ecornomereAZ.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findById(long id);

}
