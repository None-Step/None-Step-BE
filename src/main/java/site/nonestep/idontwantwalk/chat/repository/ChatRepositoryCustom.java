package site.nonestep.idontwantwalk.chat.repository;

import site.nonestep.idontwantwalk.chat.dto.*;
import site.nonestep.idontwantwalk.chat.entity.Chat;

import java.util.List;
import java.util.Optional;

public interface ChatRepositoryCustom {

    // 지역별 전체 채팅방 조회 - 입장 전
    List<ChatBeforeAllResponseDTO> selectChatBeforeAll (ChatBeforeAllRequestDTO chatBeforeAllRequestDTO);

    // 호선별 전체 채팅방 조회 - 입장 전
    List<ChatBeforeLineResponseDTO> selectChatBeforLine(ChatBeforeLineRequestDTO chatBeforeLineRequestDTO);

    // 채팅 목록 보기
    List<ChatListResponseDTO> selectMaxMessageByLine(ChatListRequestDTO chatListRequestDTO);
}
