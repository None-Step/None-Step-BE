package site.nonestep.idontwantwalk.chat.service;

import site.nonestep.idontwantwalk.chat.dto.*;
import site.nonestep.idontwantwalk.member.entity.Member;

import java.util.List;

public interface ChatService {
    // 지역 - 호선 채팅방 조회 - 입장 후(실시간)
    // 지역 - 호선 채팅방 조회(입장 후, 실시간) 을 위해 지역 - 호선 채팅방 송신 RequestDTO를 활용함
    ChatRegionLineSelectAfterResponseDTO selectWebSocketRegionLine(ChatSendRequestDTO chatSendRequestDTO, Long memberNo);

    // 지역별 전체 채팅방 조회 - 입장 전
    List<ChatBeforeAllResponseDTO> chatBeforeAll(ChatBeforeAllRequestDTO chatBeforeAllRequestDTO);

    // 호선별 전체 채팅방 조회 - 입장 전
    List<ChatBeforeLineResponseDTO> chatBeforeLine(ChatBeforeLineRequestDTO chatBeforeLineRequestDTO);

    // 실시간 채팅 삭제
    void chatDelete(ChatDeleteDTO chatDeleteDTO, Long memberNo);

    // 채팅 목록 보기
    List<ChatListResponseDTO> chatList(ChatListRequestDTO chatListRequestDTO);
}
