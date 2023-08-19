package ru.services.user;

import dtos.main.user.UserDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.mappers.UserMapper;
import ru.models.User;
import ru.repositories.UserRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public List<UserDto> getUsers(List<Long> ids, int from, int size) {
        Pageable page = PageRequest.of(from, size);

        if (ids != null)
            return UserMapper.toUserDtos(userRepository.findAllByIds(ids, page).toList());

        return UserMapper.toUserDtos(userRepository.findAll(page).toList());
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = userRepository.save(UserMapper.toUser(userDto));
        return UserMapper.toUserDto(user);
    }

    @Override
    public void removeUserById(long userId) {
        userRepository.deleteById(userId);
    }
}
