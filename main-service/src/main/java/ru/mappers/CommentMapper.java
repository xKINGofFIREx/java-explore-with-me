package ru.mappers;

import dtos.main.comment.CommentDto;
import ru.models.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentMapper {
    public static Comment toComment(CommentDto commentDto) {
        return new Comment(
                commentDto.getId(),
                null,
                null,
                commentDto.getContent()
        );
    }

    public static CommentDto toCommentDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent()
        );
    }

    public static List<CommentDto> toCommentDtos(List<Comment> comments) {
        List<CommentDto> commentDtos = new ArrayList<>();
        for (Comment comment : comments)
            commentDtos.add(toCommentDto(comment));
        return commentDtos;
    }
}
