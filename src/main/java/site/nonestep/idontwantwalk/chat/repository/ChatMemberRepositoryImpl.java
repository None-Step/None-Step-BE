package site.nonestep.idontwantwalk.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.chat.dto.ChatDeleteDTO;
import site.nonestep.idontwantwalk.chat.entity.ChatMember;
import static site.nonestep.idontwantwalk.chat.entity.QChatMember.*;

public class ChatMemberRepositoryImpl implements ChatMemberRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;

    // 실시간 채팅 삭제를 위한 조회문
    @Override
    public ChatMember selectChatNoAndMemberNo(ChatDeleteDTO chatDeleteDTO, Long memberNo) {
        return queryFactory.select(chatMember)
                .from(chatMember)
                .where(chatMember.chatRoomNo.chatNo.eq(chatDeleteDTO.getChatNo()).and(chatMember.memberNo.memberNo.eq(memberNo)))
                .fetchFirst();
    }
}
