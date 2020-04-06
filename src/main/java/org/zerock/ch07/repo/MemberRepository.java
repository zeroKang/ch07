package org.zerock.ch07.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ch07.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
