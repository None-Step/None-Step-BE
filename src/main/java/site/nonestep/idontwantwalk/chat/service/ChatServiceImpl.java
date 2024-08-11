package site.nonestep.idontwantwalk.chat.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.chat.dto.ChatRegionLineSelectAfterResponseDTO;
import site.nonestep.idontwantwalk.chat.dto.ChatSendRequestDTO;
import site.nonestep.idontwantwalk.chat.entity.Chat;
import site.nonestep.idontwantwalk.chat.entity.ChatMember;
import site.nonestep.idontwantwalk.chat.repository.ChatMemberRepository;
import site.nonestep.idontwantwalk.chat.repository.ChatRepository;
import site.nonestep.idontwantwalk.member.entity.Member;
import site.nonestep.idontwantwalk.member.repository.MemberRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ChatMemberRepository chatMemberRepository;

    // 지역 - 호선 채팅방 조회 - 입장 후(실시간)
    @Override
    public ChatRegionLineSelectAfterResponseDTO selectWebSocketRegionLine(ChatSendRequestDTO chatSendRequestDTO, Long memberNo) {
        Member member = memberRepository.getReferenceById(memberNo);
        Chat chatMSG = null;

        if (chatSendRequestDTO.getChatReply() == null) {
            chatMSG = chatRepository.save(
                    Chat.builder()
                            .chatRegion(chatSendRequestDTO.getRegion())
                            .chatLine(chatSendRequestDTO.getLine())
                            .chatMsg(chatSendRequestDTO.getMessage())
                            .chatDate(LocalDateTime.now())
                            .isChatDelete(false)
                            .isChatReport(false)
                            .build()
            );
        } else {
            Chat chat = chatRepository.getReferenceById(chatSendRequestDTO.getChatReply());

            chatMSG = chatRepository.save(
                    Chat.builder()
                            .chatRegion(chatSendRequestDTO.getRegion())
                            .chatLine(chatSendRequestDTO.getLine())
                            .chatMsg(chatSendRequestDTO.getMessage())
                            .chatDate(LocalDateTime.now())
                            .isChatDelete(false)
                            .isChatReport(false)
                            .chatReply(chat)
                            .build()
            );
        }

        chatMemberRepository.save(
                ChatMember.builder()
                        .chatRoomNo(chatMSG)
                        .memberNo(member)
                        .build());

        ChatRegionLineSelectAfterResponseDTO chatRegionLineSelectAfterResponseDTO = new ChatRegionLineSelectAfterResponseDTO();

        chatRegionLineSelectAfterResponseDTO.setChatNo(chatMSG.getChatNo());
        chatRegionLineSelectAfterResponseDTO.setMessage(chatMSG.getChatMsg());
        chatRegionLineSelectAfterResponseDTO.setDate(chatMSG.getChatDate());
        chatRegionLineSelectAfterResponseDTO.setMemberNickName(member.getMemberNickname());
        chatRegionLineSelectAfterResponseDTO.setMemberRandom(member.getMemberRandom());
        chatRegionLineSelectAfterResponseDTO.setMemberIMG(member.getMemberFile());

        if (chatMSG.getChatReply() != null) {
            chatRegionLineSelectAfterResponseDTO.setChatReply(chatMSG.getChatReply().getChatNo());
        }

        return chatRegionLineSelectAfterResponseDTO;
    }
}
