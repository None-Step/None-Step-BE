package site.nonestep.idontwantwalk.chat.service;

import site.nonestep.idontwantwalk.chat.dto.ChatRegionLineSelectAfterResponseDTO;
import site.nonestep.idontwantwalk.chat.dto.ChatSendRequestDTO;

public interface ChatService {
    // 지역 - 호선 채팅방 조회 - 입장 후(실시간)
    // 지역 - 호선 채팅방 조회(입장 후, 실시간) 을 위해 지역 - 호선 채팅방 송신 RequestDTO를 활용함
    ChatRegionLineSelectAfterResponseDTO selectWebSocketRegionLine(ChatSendRequestDTO chatSendRequestDTO, Long memberNo);
}
