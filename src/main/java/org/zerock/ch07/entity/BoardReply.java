package org.zerock.ch07.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Comparator;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardReply extends BaseEntity  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String replyText;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member replyer;

    @ManyToOne(fetch = FetchType.LAZY)
    private Board board;

}
