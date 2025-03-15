package org.expensetracker.expensetrackerapi.utils;

import lombok.experimental.UtilityClass;
import org.expensetracker.expensetrackerapi.model.user.User;
import org.expensetracker.expensetrackerapi.model.user.UserDTO;

@UtilityClass
public class UserMapper {
    public static UserDTO toDTO(User user) {
        if (user == null) return null;

        return new UserDTO(user.getId(), user.getEmail(), user.getRole());
    }

    public static User toEntity(UserDTO userDTO) {
        if (userDTO == null) return null;

        User user = new User();
        user.setEmail(userDTO.getEmail());
        return user;
    }
}
