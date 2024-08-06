package codezap.member.repository;

import java.util.Optional;

import codezap.member.domain.Member;

public interface MemberRepository {

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    Optional<Member> findByEmail(String email);

    Member save(Member member);
}
