package ru.services.comment;

import dtos.main.comment.CommentDto;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import ru.exceptions.ConflictException;
import ru.exceptions.NotFoundException;
import ru.mappers.CommentMapper;
import ru.models.Comment;
import ru.repositories.CommentRepository;
import ru.repositories.EventRepository;
import ru.repositories.UserRepository;

import java.util.List;

@Repository
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public List<CommentDto> getComments(int from, int size) {
        return CommentMapper.toCommentDtos(
                commentRepository.findAll(PageRequest.of(from, size)).toList());
    }

    @Override
    public CommentDto createComment(long userId, long eventId, CommentDto commentDto) throws NotFoundException {
        Comment comment = checkCommentDto(userId, eventId, commentDto);

        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public CommentDto updateComment(long userId, long eventId, CommentDto commentDto) throws NotFoundException {
        Comment comment = checkCommentDto(userId, eventId, commentDto);

        return CommentMapper.toCommentDto(commentRepository.save(comment));
    }

    @Override
    public void deleteCommentUser(long userId, long commentId) throws NotFoundException, ConflictException {
        if (!userRepository.existsById(userId))
            throw new NotFoundException("Пользователь не найден");

        if (!commentRepository.existsById(commentId))
            throw new NotFoundException("Комментарий не найден");

        if (commentRepository.getReferenceById(commentId).getUser().getId() != userId)
            throw new ConflictException("Пользователь не может удалить чужой комментарий");
        commentRepository.deleteById(commentId);
    }

    @Override
    public void deleteCommentAdmin(long commentId) throws NotFoundException {
        if (!commentRepository.existsById(commentId))
            throw new NotFoundException("Комментарий не найден");

        commentRepository.deleteById(commentId);
    }

    private Comment checkCommentDto(long userId, long eventId, CommentDto commentDto) throws NotFoundException {
        if (!userRepository.existsById(userId))
            throw new NotFoundException("Пользователь не найден");

        if (!eventRepository.existsById(eventId))
            throw new NotFoundException("События не найдено");

        Comment comment = CommentMapper.toComment(commentDto);
        comment.setUser(userRepository.getReferenceById(userId));
        comment.setEvent(eventRepository.getReferenceById(eventId));
        return comment;
    }
}
