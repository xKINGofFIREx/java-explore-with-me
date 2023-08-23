package ru.services.comment;

import dtos.main.comment.CommentDto;
import ru.exceptions.ConflictException;
import ru.exceptions.NotFoundException;

import java.util.List;

public interface CommentService {
    List<CommentDto> getComments(int from, int size);

    CommentDto createComment(long userId, long eventId, CommentDto commentDto) throws NotFoundException;

    CommentDto updateComment(long userId, long eventId, CommentDto commentDto) throws NotFoundException;

    void deleteCommentUser(long userId, long commentId) throws NotFoundException, ConflictException;

    void deleteCommentAdmin(long commentId) throws NotFoundException;
}
