package site.nonestep.idontwantwalk.chat.repository;

import site.nonestep.idontwantwalk.chat.dto.ChatDeleteDTO;
import site.nonestep.idontwantwalk.chat.entity.ChatMember;

public interface ChatMemberRepositoryCustom {
    // 실시간 채팅 삭제를 위해 memberNo와 chatRoomNo를 하나로 합침
    ChatMember selectChatNoAndMemberNo(ChatDeleteDTO chatDeleteDTO, Long memberNo);
}
