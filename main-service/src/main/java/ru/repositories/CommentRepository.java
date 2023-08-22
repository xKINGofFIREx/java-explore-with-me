package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
