package site.nonestep.idontwantwalk.chat.entity;

import jakarta.persistence.*;
import lombok.*;
import site.nonestep.idontwantwalk.member.entity.Member;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@IdClass(ChatMemberRelay.class)
public class ChatMember {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long chatMemberNo;
//    chatMemberNo 만들면 안되는 이유 : 무한대로 방이 생겨서 error남

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no", nullable = false)
    private Member memberNo;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_no", nullable = false)
    private Chat chatRoomNo;
}
