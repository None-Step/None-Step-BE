package site.nonestep.idontwantwalk.chat.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import site.nonestep.idontwantwalk.chat.dto.*;
import site.nonestep.idontwantwalk.chat.entity.Chat;

import java.util.List;
import static site.nonestep.idontwantwalk.chat.entity.QChat.*;
import static site.nonestep.idontwantwalk.member.entity.QMember.*;
import static site.nonestep.idontwantwalk.chat.entity.QChatMember.*;

public class ChatRepositoryImpl implements ChatRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;

    // 지역별 전체 채팅방 조회 - 입장 전
    @Override
    public List<ChatBeforeAllResponseDTO> selectChatBeforeAll(ChatBeforeAllRequestDTO chatBeforeAllRequestDTO) {
        return queryFactory.select(Projections.constructor(ChatBeforeAllResponseDTO.class, chat.chatNo, chat.chatLine,
                        chat.chatMsg, chat.chatDate, member.memberNickName, member.memberRandom, member.memberFile, chat.chatReply.chatNo, chat.isChatDelete))
                .from(chat)
                .join(chatMember)
                .on(chatMember.chatRoomNo.eq(chat))
                .join(member)
                .on(member.memberNo.eq(chatMember.memberNo.memberNo))
                .where(chat.chatRegion.eq(chatBeforeAllRequestDTO.getRegion()))
                .fetch();
    }

    // 호선별 전체 채팅방 조회 - 입장 전
    @Override
    public List<ChatBeforeLineResponseDTO> selectChatBeforLine(ChatBeforeLineRequestDTO chatBeforeLineRequestDTO) {
        return queryFactory.select(Projections.constructor(ChatBeforeLineResponseDTO.class, chat.chatNo, chat.chatMsg,
                        chat.chatDate, member.memberNickName, member.memberRandom, member.memberFile, chat.chatReply.chatNo, chat.isChatDelete))
                .from(chat)
                .join(chatMember)
                .on(chatMember.chatRoomNo.eq(chat))
                .join(member)
                .on(member.memberNo.eq(chatMember.memberNo.memberNo))
                .where(chat.chatRegion.eq(chatBeforeLineRequestDTO.getRegion()).and(chat.chatLine.eq(chatBeforeLineRequestDTO.getLine())))
                .fetch();
    }

    // 채팅 목록 보기
    @Override
    public List<ChatListResponseDTO> selectMaxMessageByLine(ChatListRequestDTO chatListRequestDTO) {
        return queryFactory.select(Projections.constructor(ChatListResponseDTO.class, chat.chatLine, chat.chatDate.max()))
                .from(chat)
                .where(chat.chatRegion.eq(chatListRequestDTO.getRegion()))
                .groupBy(chat.chatLine)
                .fetch();
    }
}
