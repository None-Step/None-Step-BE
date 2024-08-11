package site.nonestep.idontwantwalk.chat.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_no")
    private Long chatNo;

    @Column(name = "chat_region", length = 100, nullable = false)
    private String chatRegion;

    @Column(name = "chat_line", length = 100, nullable = false)
    private String chatLine;

    @Column(name = "chat_msg", length = 1500, nullable = false)
    private String chatMsg;

    @Column(name = "chat_date")
    private LocalDateTime chatDate;

    @Builder.Default
    @Column(name = "chat_isdelete")
    private Boolean isChatDelete = false;

    @Builder.Default
    @Column(name = "chat_isreport")
    private Boolean isChatReport = false;

    // 채팅 답 멘션 관련
    // 채팅 테이블 자기 순환 참조
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_reply", nullable = true)
    private Chat chatReply;


}
