package com.library.lms.repository;

import com.library.lms.entity.book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<book, Long> {

    Optional<book> findByIsbn(String isbn);

    List<book> findByTitleContainingIgnoreCase(String title);

    List<book> findByAuthorContainingIgnoreCase(String author);

    List<book> findByCategory(String category);

    List<book> findByAvailableCopiesGreaterThan(int copies);
}