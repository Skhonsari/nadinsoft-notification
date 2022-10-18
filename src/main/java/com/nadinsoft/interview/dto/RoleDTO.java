package com.nadinsoft.interview.dto;

import com.nadinsoft.interview.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class RoleDTO {

    private Long id;
    private String name;
    private List<UserDTO> users;

    public static RoleDTO load(Role role) {
        final RoleDTO dto = new RoleDTO();
        dto.setId(role.getId());
        dto.setName(role.getName());
        dto.setUsers(role.getUsers().stream().map(UserDTO::load).collect(Collectors.toList()));

        return dto;
    }
}
