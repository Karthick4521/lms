
package com.library.lms.repository;

import com.library.lms.entity.BorrowRecord;
import com.library.lms.entity.member;
import com.library.lms.entity.book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    List<BorrowRecord> findByMember(member member);

    List<BorrowRecord> findByBook(book book);

    List<BorrowRecord> findByStatus(BorrowRecord.BorrowStatus status);

    List<BorrowRecord> findByMemberAndStatus(member member, BorrowRecord.BorrowStatus status);

    List<BorrowRecord> findByDueDateBeforeAndStatus(LocalDate date, BorrowRecord.BorrowStatus status);
}