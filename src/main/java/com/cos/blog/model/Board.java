package com.cos.blog.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private int id;

    @Column(nullable = false, length = 100)
    private String title;

    @Lob//대용량 데이터
    private String content;// 섬머노트 라이브러리 <html>태그가 섞여서 디자인 됨.

    //@ColumnDefault("0")
    private int count;//조회수

    @ManyToOne(fetch = FetchType.EAGER) //Many = Board, User = One
    @JoinColumn(name = "userId")
    private User user; //DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다.

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE) //mappedBy 연관관계의 주인이 아니다(난 FK가 아니다) DB에 컬럼을 만들지 않는다. cascade = board에 연관관계된 replys에 대한 설정.(CascadeType.REMOVE = 모두 삭제)
    @JsonIgnoreProperties({"board"}) //순환참조 방지. reply를 조회 할 때는 안에있는 board를 다시 조회하지 않는다.
    @OrderBy("id desc")
    private List<Reply> replys;

    @CreationTimestamp
    private Timestamp createDate;
}
