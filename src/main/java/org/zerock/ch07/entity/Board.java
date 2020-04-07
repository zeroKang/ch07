package org.zerock.ch07.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Board extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bno;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member writer;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "board")
    private Set<BoardFile> fileSet;


    public void setWriterWithMno(Long mno) {
        this.writer = Member.builder().mno(mno).build();
    }
}
