package com.library.lms.service;

import com.library.lms.entity.member;
import com.library.lms.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MemberService {

    @Autowired
    private MemberRepository memberRepository;

    public List<member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Optional<member> getMemberById(Long id) {
        return memberRepository.findById(id);
    }

    public member addMember(member member) {
        return memberRepository.save(member);
    }

    public member updateMember(Long id, member memberDetails) {
        member member = memberRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        member.setName(memberDetails.getName());
        member.setEmail(memberDetails.getEmail());
        member.setPhone(memberDetails.getPhone());
        member.setStatus(memberDetails.getStatus());
        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public List<member> searchByName(String name) {
        return memberRepository.findByNameContainingIgnoreCase(name);
    }

    public List<member> getActiveMembers() {
        return memberRepository.findByStatus(member.MemberStatus.ACTIVE);
    }
}