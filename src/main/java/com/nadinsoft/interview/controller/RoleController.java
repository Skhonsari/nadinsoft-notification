package com.nadinsoft.interview.controller;

import com.nadinsoft.interview.dto.RoleDTO;
import com.nadinsoft.interview.dto.UserDTO;
import com.nadinsoft.interview.model.Role;
import com.nadinsoft.interview.model.User;
import com.nadinsoft.interview.repository.RoleRepository;
import com.nadinsoft.interview.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/role")
public class RoleController {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @PostMapping
    public RoleDTO addRole(@RequestBody RoleDTO role) {
        final Role role1 = new Role();
        role1.setName(role.getName());
        final Set<User> users = userRepository.findAllByUsernameIn(role.getUsers().stream().map(UserDTO::getUsername).collect(Collectors.toList()));
        if (users.size() == 0) {
            throw new IllegalArgumentException("no user found with specified data");
        }
        role1.setUsers(users);
        return RoleDTO.load(roleRepository.save(role1));
    }

    @GetMapping
    public List<RoleDTO> getAll() {
        return roleRepository.findAll().stream().map(RoleDTO::load).collect(Collectors.toList());
    }

}
