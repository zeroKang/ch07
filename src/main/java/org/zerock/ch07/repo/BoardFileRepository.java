package org.zerock.ch07.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.ch07.entity.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {
}
