package com.nadinsoft.interview.dto;

import com.nadinsoft.interview.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private String username;
    private String password;

    public static UserDTO load(User user) {
        final UserDTO dto = new UserDTO();
        dto.setUsername(user.getUsername());
        return dto;
    }
}
