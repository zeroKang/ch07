package org.zerock.ch07.repo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.ch07.entity.Board;
import org.zerock.ch07.entity.BoardFile;
import org.zerock.ch07.entity.Member;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.stream.IntStream;

@SpringBootTest
@Transactional
public class RepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardFileRepository fileRepository;

    @Test
    @Commit
    public void testInsertMembers() {

        IntStream.range(1,101).forEach(i -> {
            Member member = Member.builder().mid("U"+i).mpw("U"+i).mname("USER"+ i).build();
            System.out.println(memberRepository.save(member));
        });
    }


    @Test
    @Commit
    public void testInsertBoards() {

        IntStream.range(1,101).forEach(i -> {
            Board board = Board.builder().title("Test" + i).content("Content..." + i).build();
            board.setWriterWithMno((long)i);

            System.out.println(boardRepository.save(board));
        });
    }

    @Test
    public void testReadBoard() {

        Board board = boardRepository.findById(10L).get();

        System.out.println(board);
        System.out.println("--------------------------");
        System.out.println(board.getBno());
        System.out.println(board.getTitle());
        System.out.println(board.getContent());
        System.out.println(board.getWriter());

    }

    @Test
    public void testReadBoardWithFetch() {

        Board board = boardRepository.getBoardWithFetch(10L);

        System.out.println(board);
        System.out.println("--------------------------");
        System.out.println(board.getBno());
        System.out.println(board.getTitle());
        System.out.println(board.getContent());
        System.out.println(board.getWriter());

    }

    @Test
    @Commit
    public void testBoardWithFiles(){

        Board board = Board.builder().bno(100L).build();

        IntStream.range(1,5).forEach(i-> {

            BoardFile file = BoardFile.builder().fname("image"+ i +".jpg").board(board).build();

            board.addFile(file);

            fileRepository.save(file);
        });
    }

    @Test
    public void testReadBoardWithFile() {

        Board board = boardRepository.findById(100L).get();

        System.out.println(board.getBno());
        System.out.println(board.getTitle());
        System.out.println("----------------------");
        System.out.println(board.getWriter());
        System.out.println("----------------------");
        System.out.println(board.getFileSet());
    }

    @Test
    public void testReadBoardWithFile2(){

        Board board = boardRepository.getBoardAllWithFetch(10L);

        System.out.println(board.getBno());
        System.out.println(board.getTitle());
        System.out.println("----------------------");
        System.out.println(board.getWriter());
        System.out.println("----------------------");
        System.out.println(board.getFileSet());

    }

    @Test
    public void testBoardPaging() {

        Sort sort = Sort.by("bno").descending();

        PageRequest pageRequest = PageRequest.of(0,20,sort);

        Page<Object[]> result = boardRepository.getBoardAllWithFetch(pageRequest);

        System.out.println(result);

        System.out.println("-----------------------------");

        result.getContent().forEach(arr -> {
            System.out.println(Arrays.toString(arr));
            System.out.println("----------------------------------");
        });

    }



}
