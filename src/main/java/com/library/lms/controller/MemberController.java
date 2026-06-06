package com.library.lms.controller;

import com.library.lms.entity.member;
import com.library.lms.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@CrossOrigin(origins = "*")
public class MemberController {

    @Autowired
    private MemberService memberService;

    // GET all members
    @GetMapping
    public ResponseEntity<List<member>> getAllMembers() {
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    // GET member by ID
    @GetMapping("/{id}")
    public ResponseEntity<member> getMemberById(@PathVariable Long id) {
        return memberService.getMemberById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST add new member
    @PostMapping
    public ResponseEntity<member> addMember( @RequestBody member member) {
        member saved = memberService.addMember(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // PUT update member
    @PutMapping("/{id}")
    public ResponseEntity<member> updateMember(
            @PathVariable Long id,
             @RequestBody member member) {
        try {
            return ResponseEntity.ok(memberService.updateMember(id, member));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // DELETE member
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.noContent().build();
    }

    // GET search by name
    @GetMapping("/search")
    public ResponseEntity<List<member>> searchByName(
            @RequestParam String name) {
        return ResponseEntity.ok(memberService.searchByName(name));
    }

    // GET active members
    @GetMapping("/active")
    public ResponseEntity<List<member>> getActiveMembers() {
        return ResponseEntity.ok(memberService.getActiveMembers());
    }
}