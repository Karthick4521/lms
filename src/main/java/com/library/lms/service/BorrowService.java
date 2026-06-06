package com.library.lms.service;

import com.library.lms.entity.book;
import com.library.lms.entity.BorrowRecord;
import com.library.lms.entity.member;
import com.library.lms.repository.BookRepository;
import com.library.lms.repository.BorrowRecordRepository;
import com.library.lms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BorrowService {

    @Autowired
    private BorrowRecordRepository borrowRecordRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MemberRepository memberRepository;

    public BorrowRecord borrowBook(Long bookId, Long memberId) {
        book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        if (book.getAvailableCopies() <= 0) {
            throw new RuntimeException("No copies available");
        }

        book.setAvailableCopies(book.getAvailableCopies() - 1);
        bookRepository.save(book);

        BorrowRecord record = new BorrowRecord();
        record.setBook(book);
        record.setMember(member);
        record.setBorrowDate(LocalDate.now());
        record.setDueDate(LocalDate.now().plusDays(14));
        record.setStatus(BorrowRecord.BorrowStatus.BORROWED);

        return borrowRecordRepository.save(record);
    }

    public BorrowRecord returnBook(Long recordId) {
        BorrowRecord record = borrowRecordRepository.findById(recordId)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        record.setReturnDate(LocalDate.now());
        record.setStatus(BorrowRecord.BorrowStatus.RETURNED);

        book book = record.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        bookRepository.save(book);

        return borrowRecordRepository.save(record);
    }

    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.findAll();
    }

    public List<BorrowRecord> getOverdueBooks() {
        return borrowRecordRepository.findByDueDateBeforeAndStatus(
                LocalDate.now(), BorrowRecord.BorrowStatus.BORROWED);
    }

    public List<BorrowRecord> getMemberBorrowHistory(Long memberId) {
        member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        return borrowRecordRepository.findByMember(member);
    }
}