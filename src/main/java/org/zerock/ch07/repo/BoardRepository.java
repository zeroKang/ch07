package org.zerock.ch07.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.ch07.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b JOIN FETCH b.writer WHERE b.bno = :bno ")
    Board getBoardWithFetch(Long bno);

    @Query("SELECT b FROM Board b JOIN FETCH b.writer w LEFT JOIN FETCH b.fileSet f WHERE b.bno = :bno")
    Board getBoardAllWithFetch(Long bno);

    @Query("SELECT b.bno, b.title, b.writer.mno, b.writer.mname, count(f) FROM Board b INNER JOIN Member m ON m = b.writer LEFT OUTER JOIN BoardFile f ON f.board = b GROUP BY b")
    Page<Object[]> getBoardAllWithFetch(Pageable pageable);

}