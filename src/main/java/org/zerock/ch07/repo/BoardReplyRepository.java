package org.zerock.ch07.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ch07.entity.BoardReply;

public interface BoardReplyRepository extends JpaRepository<BoardReply, Long> {
}
