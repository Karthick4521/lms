package com.library.lms.repository;

import com.library.lms.entity.member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<member, Long> {

    Optional<member> findByEmail(String email);

    List<member> findByNameContainingIgnoreCase(String name);

    List<member> findByStatus(member.MemberStatus status);
}
