package ru.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.models.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
