package vn.ecornomere.ecornomereAZ.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.ecornomere.ecornomereAZ.model.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findById(long id);

    Role findByName(String name);

}
