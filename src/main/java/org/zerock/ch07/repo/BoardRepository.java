package org.zerock.ch07.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.ch07.entity.Board;

import javax.persistence.OrderBy;

public interface BoardRepository extends JpaRepository<Board, Long> {

    @Query("SELECT b FROM Board b JOIN FETCH b.writer w LEFT JOIN FETCH b.fileSet f WHERE b.bno = :bno")
    Board getBoardAllWithFetch(Long bno);

//    @EntityGraph(attributePaths = {"fileSet", "writer"}, type = EntityGraph.EntityGraphType.LOAD)
//    @Query("SELECT b FROM Board b ")
//    Page<Board> getPage(Pageable pageable);

    @EntityGraph(attributePaths = {"fileSet", "writer","replySet"},
            type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT b FROM Board b")
    Page<Board> getPage(Pageable pageable);
}