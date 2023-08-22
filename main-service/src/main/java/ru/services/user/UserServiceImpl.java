package ru.services.user;

import dtos.main.request.NewUserRequest;
import dtos.main.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.mappers.UserMapper;
import ru.models.User;
import ru.repositories.UserRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        Pageable page = PageRequest.of(from, size);
        List<User> users;

        if (ids != null)
            users = userRepository.findAllByIds(ids, page).toList();
        else
            users = userRepository.findAll(page).toList();

        users = users.stream().sorted(Comparator.comparing(User::getId)).collect(Collectors.toList());
        return UserMapper.toUserDtos(users);
    }

    @Override
    public UserDto createUser(NewUserRequest newUserRequest) {
        User user = userRepository.save(UserMapper.toUser(newUserRequest));
        return UserMapper.toUserDto(user);
    }

    @Override
    public void removeUserById(long userId) {
        userRepository.deleteById(userId);
    }
}
