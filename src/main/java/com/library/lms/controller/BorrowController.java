package com.library.lms.controller;

import com.library.lms.entity.BorrowRecord;
import com.library.lms.service.BorrowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow")
@CrossOrigin(origins = "*")
public class BorrowController {

    @Autowired
    private BorrowService borrowService;

    // POST borrow a book
    @PostMapping("/issue")
    public ResponseEntity<?> borrowBook(
            @RequestParam Long bookId,
            @RequestParam Long memberId) {
        try {
            BorrowRecord record = borrowService.borrowBook(bookId, memberId);
            return ResponseEntity.status(HttpStatus.CREATED).body(record);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // PUT return a book
    @PutMapping("/return/{recordId}")
    public ResponseEntity<?> returnBook(@PathVariable Long recordId) {
        try {
            BorrowRecord record = borrowService.returnBook(recordId);
            return ResponseEntity.ok(record);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // GET all borrow records
    @GetMapping
    public ResponseEntity<List<BorrowRecord>> getAllRecords() {
        return ResponseEntity.ok(borrowService.getAllBorrowRecords());
    }

    // GET overdue books
    @GetMapping("/overdue")
    public ResponseEntity<List<BorrowRecord>> getOverdueBooks() {
        return ResponseEntity.ok(borrowService.getOverdueBooks());
    }

    // GET member borrow history
    @GetMapping("/member/{memberId}")
    public ResponseEntity<?> getMemberHistory(@PathVariable Long memberId) {
        try {
            return ResponseEntity.ok(borrowService.getMemberBorrowHistory(memberId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}