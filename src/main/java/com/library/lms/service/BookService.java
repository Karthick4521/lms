package com.library.lms.service;

import com.library.lms.entity.book;
import com.library.lms.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public book addBook(book book) {
        return bookRepository.save(book);
    }

    public book updateBook(Long id, book bookDetails) {
        book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        book.setTitle(bookDetails.getTitle());
        book.setAuthor(bookDetails.getAuthor());
        book.setIsbn(bookDetails.getIsbn());
        book.setCategory(bookDetails.getCategory());
        book.setTotalCopies(bookDetails.getTotalCopies());
        book.setAvailableCopies(bookDetails.getAvailableCopies());
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    public List<book> searchByTitle(String title) {
        return bookRepository.findByTitleContainingIgnoreCase(title);
    }

    public List<book> searchByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }

    public List<book> getAvailableBooks() {
        return bookRepository.findByAvailableCopiesGreaterThan(0);
    }
    }
