package vn.ecornomere.ecornomereAZ.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import vn.ecornomere.ecornomereAZ.repository.RoleRepository;
import vn.ecornomere.ecornomereAZ.model.Role;

@Service
public class RoleService {
    private RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRoleId(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role Id: " + roleId));

    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

}
