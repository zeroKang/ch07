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
import org.zerock.ch07.entity.BoardReply;
import org.zerock.ch07.entity.Member;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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

    @Autowired
    private BoardReplyRepository replyRepository;

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

        Board board = boardRepository.findById(100L).get();

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

        Board board = boardRepository.getBoardAllWithFetch(100L);

        System.out.println(board.getBno());
        System.out.println(board.getTitle());
        System.out.println("----------------------");
        System.out.println(board.getWriter());
        System.out.println("----------------------");
        System.out.println(board.getFileSet());

    }

    @Test
    public void testBoardPagingEntityGraph(){

        Sort sort = Sort.by("bno").descending();

        PageRequest pageRequest = PageRequest.of(0,20,sort);

        Page<Board> result = boardRepository.getPage(pageRequest);

        System.out.println(result);

        System.out.println("-----------------------------");

        result.getContent().forEach(board -> {
            System.out.print(board.getBno() +"\t");
            System.out.print(board.getTitle() + "\t");
            System.out.print(board.getWriter().getMname() +"\t");
            System.out.print(board.getRegDate() +"\t");
            System.out.println(board.getFileSet() );
            System.out.println("----------------------------------");
        });

    }

    @Test
    @Commit
    public void testAddReply(){

        Board board = Board.builder().bno(100L).build();

        IntStream.range(1, 11).forEach(i -> {

            Member replyer = Member.builder().mno((long)i).build();

            BoardReply reply = BoardReply.builder().replyText("Reply 100... " + i)
                    .replyer(replyer)
                    .board(board)
                    .build();

            replyRepository.save(reply);
        });
    }

    @Test
    public void testBoardPagingEntityGraphReply(){

        Sort sort = Sort.by("bno").descending();

        PageRequest pageRequest = PageRequest.of(5,20,sort);

        Page<Board> result = boardRepository.getPage(pageRequest);

        System.out.println(result);

        System.out.println("-----------------------------");

        result.getContent().forEach(board -> {
            System.out.print(board.getBno() +"\t");
            System.out.print(board.getTitle() + "\t");
            System.out.print(board.getWriter().getMname() +"\t");
            System.out.print(board.getRegDate() +"\t");
            System.out.println(board.getFileSet() );

            Set<BoardReply> replySet =  board.getReplySet();

            List<BoardReply> replyList = replySet.stream().collect(Collectors.toList());

            Collections.sort(replyList, (o1, o2) -> o1.getRno().compareTo(o2.getRno()));

            for (BoardReply reply : replyList) {
                System.out.println(reply.getRno() +" : " + reply.getReplyText());
            }


            System.out.println("----------------------------------");
        });

    }

}
