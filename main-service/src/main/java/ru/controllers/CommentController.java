package ru.controllers;

import dtos.main.comment.CommentDto;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.exceptions.ConflictException;
import ru.exceptions.NotFoundException;
import ru.services.comment.CommentService;

import java.util.List;

@RestController
@AllArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/comments")
    public ResponseEntity<List<CommentDto>> getComments(@RequestParam(required = false, defaultValue = "0") int from,
                                                        @RequestParam(required = false, defaultValue = "10") int size) {
        return new ResponseEntity<>(commentService.getComments(from, size), HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/comments/{eventId}")
    public ResponseEntity<CommentDto> createComment(@PathVariable("userId") long userId,
                                                    @PathVariable("eventId") long commentId,
                                                    @RequestBody CommentDto commentDto) throws NotFoundException {
        return new ResponseEntity<>(commentService.createComment(userId, commentId, commentDto), HttpStatus.CREATED);
    }

    @PatchMapping("/users/{userId}/comments/{eventId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable("userId") long userId,
                                                    @PathVariable("eventId") long commentId,
                                                    @RequestBody CommentDto commentDto) throws NotFoundException {
        return new ResponseEntity<>(commentService.updateComment(userId, commentId, commentDto), HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/comments/{commentId}")
    public ResponseEntity<CommentDto> deleteCommentUser(@PathVariable("userId") long userId,
                                                        @PathVariable("commentId") long commentId) throws NotFoundException, ConflictException {
        commentService.deleteCommentUser(userId, commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/admin/comments/{commentId}")
    public ResponseEntity<CommentDto> deleteCommentAdmin(@PathVariable("commentId") long commentId) throws NotFoundException {
        commentService.deleteCommentAdmin(commentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
