package com.library.lms.controller;

import com.library.lms.entity.book;
import com.library.lms.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    // GET all books
    @GetMapping
    public ResponseEntity<List<book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    // GET book by ID
    @GetMapping("/{id}")
    public ResponseEntity<book> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST add new book
    @PostMapping
    public ResponseEntity<book> addBook( @RequestBody book book) {
        book saved = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT update book
    @PutMapping("/{id}")
    public ResponseEntity<book> updateBook(
            @PathVariable Long id,
             @RequestBody book book) {
        try {
            return ResponseEntity.ok(bookService.updateBook(id, book));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE book
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // GET search by title
    @GetMapping("/search/title")
    public ResponseEntity<List<book>> searchByTitle(
            @RequestParam String title) {
        return ResponseEntity.ok(bookService.searchByTitle(title));
    }

    // GET search by author
    @GetMapping("/search/author")
    public ResponseEntity<List<book>> searchByAuthor(
            @RequestParam String author) {
        return ResponseEntity.ok(bookService.searchByAuthor(author));
    }

    // GET available books
    @GetMapping("/available")
    public ResponseEntity<List<book>> getAvailableBooks() {
        return ResponseEntity.ok(bookService.getAvailableBooks());
    }
}