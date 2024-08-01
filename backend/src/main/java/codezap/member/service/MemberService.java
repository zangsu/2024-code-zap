package codezap.member.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import codezap.member.dto.SignupRequest;
import codezap.global.exception.CodeZapException;
import codezap.member.domain.Member;
import codezap.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public long signup(SignupRequest request) {
        validateSignupRequest(request);
        Member member = new Member(request.email(), request.password(), request.username());
        Member saved = memberRepository.save(member);
        return saved.getId();
    }

    private void validateSignupRequest(SignupRequest request) {
        validateNotDuplicateEmail(request.email());
        validateNotDuplicateUsername(request.username());
    }

    private void validateNotDuplicateEmail(String email) {
        if (!isUniqueEmail(email)) {
            throw new CodeZapException(HttpStatus.CONFLICT, "이메일이 이미 존재합니다.");
        }
    }

    private void validateNotDuplicateUsername(String username) {
        if (!isUniqueUsername(username)) {
            throw new CodeZapException(HttpStatus.CONFLICT, "사용자명이 이미 존재합니다.");
        }
    }

    public boolean isUniqueEmail(String email) {
        return !memberRepository.existsByEmail(email);
    }

    public boolean isUniqueUsername(String username) {
        return !memberRepository.existsByUsername(username);
    }
}
