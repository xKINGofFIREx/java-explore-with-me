package ru.mappers;

import dtos.main.request.NewUserRequest;
import dtos.main.user.UserDto;
import dtos.main.user.UserShortDto;
import ru.models.User;

import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getName()
        );
    }

    public static User toUser(NewUserRequest newUserRequest) {
        return new User(
                0,
                newUserRequest.getName(),
                newUserRequest.getEmail()
        );
    }

    public static UserShortDto toUserShortDto(User user) {
        return new UserShortDto(user.getId(), user.getName());
    }

    public static List<UserDto> toUserDtos(List<User> users) {
        List<UserDto> userDtos = new ArrayList<>();
        for (User user : users)
            userDtos.add(toUserDto(user));
        return userDtos;
    }
}
