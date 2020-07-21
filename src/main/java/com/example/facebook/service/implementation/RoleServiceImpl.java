package com.example.facebook.service.implementation;

import com.example.facebook.entity.Role;
import com.example.facebook.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class RoleServiceImpl {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getUserRole() {
        Role userRole = roleRepository.findFirstByAuthority("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("User role not found"));

        return userRole;
    }

    @PostConstruct
    private void generateClasses() {
        String userRole = "ROLE_USER";
        Role role = roleRepository.findFirstByAuthority(userRole).orElse(null);
        if (role == null) {
            role = new Role();
            role.setAuthority(userRole);
            roleRepository.save(role);
        }
    }
}
