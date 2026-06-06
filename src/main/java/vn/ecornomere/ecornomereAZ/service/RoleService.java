package vn.ecornomere.ecornomereAZ.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import vn.ecornomere.ecornomereAZ.model.entity.Role;
import vn.ecornomere.ecornomereAZ.repository.RoleRepository;

@Service
@RequiredArgsConstructor
public class RoleService {
    private final RoleRepository roleRepository;


    public Role findRoleId(Long roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid role Id: " + roleId));

    }

    public Role findRoleByName(String name) {
        return roleRepository.findByName(name);
    }

}
