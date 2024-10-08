package site.nonestep.idontwantwalk.board.entity;

import jakarta.persistence.Entity;
import lombok.*;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;
import site.nonestep.idontwantwalk.member.entity.Member;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder

public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boardNo;

    @Column(name = "board_title", length = 500, nullable = false)
    private String boardTitle;

    @Column(name = "board_content", length = 5000, nullable = false)
    private String boardContent;

    @Column(name = "board_img", length = 3000)
    private String boardImg;

    @Column(name = "board_write_date")
    private LocalDateTime boardWriteDate;

    @Column(name = "board_modify_date")
    private LocalDateTime boardModifyDate;

    @ManyToOne
    @JoinColumn(name = "member_no", nullable = false)
    private Member memberNo;

    //게시글 수정
    public void modifyBoard(String boardTitle, String boardContent) {
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardModifyDate = LocalDateTime.now();
    }
}
