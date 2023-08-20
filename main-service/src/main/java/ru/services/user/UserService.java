package ru.services.user;

import dtos.main.request.NewUserRequest;
import dtos.main.user.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getUsers(List<Long> ids, int from, int size);

    UserDto createUser(NewUserRequest newUserRequest);

    void removeUserById(long userId);
}
