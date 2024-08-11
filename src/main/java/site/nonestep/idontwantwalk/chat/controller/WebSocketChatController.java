package site.nonestep.idontwantwalk.chat.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import site.nonestep.idontwantwalk.auth.jwt.JsonWebTokenProvider;
import site.nonestep.idontwantwalk.auth.util.JwtTokenUtils;
import site.nonestep.idontwantwalk.chat.dto.ChatDeleteDTO;
import site.nonestep.idontwantwalk.chat.dto.ChatRegionLineSelectAfterResponseDTO;
import site.nonestep.idontwantwalk.chat.dto.ChatSendRequestDTO;
import site.nonestep.idontwantwalk.chat.service.ChatService;

import java.nio.charset.StandardCharsets;

@Slf4j
@RequiredArgsConstructor
@Controller
public class WebSocketChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final ChatService chatService;
    private final JsonWebTokenProvider jsonWebTokenProvider;

    // FE 입장에서는 송신 부분
    // 앞에 자동으로 /pub 붙음
    @MessageMapping("/chat")
    public void chatSend(ChatSendRequestDTO chatSendRequestDTO, @Header("Authorization") String authorization ){

        String engChangeKorRegion = chatSendRequestDTO.getRegion();
        if("seoul".equals(engChangeKorRegion)){
            engChangeKorRegion = "수도권";
        } else if ("busan".equals(engChangeKorRegion)) {
            engChangeKorRegion = "부산";
        } else if ("daegu".equals(engChangeKorRegion)) {
            engChangeKorRegion = "대구";
        } else if ("daejeon".equals(engChangeKorRegion)) {
            engChangeKorRegion = "대전";
        } else if ("gwangju".equals(engChangeKorRegion)) {
            engChangeKorRegion = "광주";
        }

        String engChangeKorLine = chatSendRequestDTO.getLine();

        if ("line1".equals(engChangeKorLine)){
            engChangeKorLine = "1호선";
        }else if("line2".equals(engChangeKorLine)){
            engChangeKorLine = "2호선";
        } else if ("line3".equals(engChangeKorLine)) {
            engChangeKorLine = "3호선";
        }else if ("line4".equals(engChangeKorLine)){
            engChangeKorLine = "4호선";
        }else if ("line5".equals(engChangeKorLine)){
            engChangeKorLine = "5호선";
        } else if ("line6".equals(engChangeKorLine)) {
            engChangeKorLine = "6호선";
        }else if ("line7".equals(engChangeKorLine)){
            engChangeKorLine = "7호선";
        } else if ("line8".equals(engChangeKorLine)) {
            engChangeKorLine = "8호선";
        } else if ("line9".equals(engChangeKorLine)) {
            engChangeKorLine = "9호선";
        } else if ("suInBunDangLine".equals(engChangeKorLine)) {
            engChangeKorLine = "수인분당선";
        } else if ("shinBunDangLine".equals(engChangeKorLine)) {
            engChangeKorLine = "신분당선";
        } else if ("gyeongUiJungAngLine".equals(engChangeKorLine)) {
            engChangeKorLine = "경의중앙선";
        } else if ("gyeongChunLine".equals(engChangeKorLine)) {
            engChangeKorLine = "경춘선";
        } else if ("gyeongGangLine".equals(engChangeKorLine)) {
            engChangeKorLine = "경강선";
        } else if ("uiSinSeolLine".equals(engChangeKorLine)) {
            engChangeKorLine = "우의신설선";
        } else if ("silLimLine".equals(engChangeKorLine)) {
            engChangeKorLine = "신림선";
        } else if ("gimPoGoldLine".equals(engChangeKorLine)) {
            engChangeKorLine = "김포골드라인";
        } else if ("everLine".equals(engChangeKorLine)) {
            engChangeKorLine = "에버라인";
        }else if ("seoHaeLine".equals(engChangeKorLine)){
            engChangeKorLine = "서해선";
        } else if ("gongHangCheolDo".equals(engChangeKorLine)) {
            engChangeKorLine = "공항철도";
        } else if ("gtxA".equals(engChangeKorLine)) {
            engChangeKorLine = "GTX-A";
        } else if ("uiJeongBu".equals(engChangeKorLine)) {
            engChangeKorLine = "의정부경전철";
        }else if ("inCheonLine1".equals(engChangeKorLine)){
            engChangeKorLine = "인천1호선";
        } else if ("inCheonLine2".equals(engChangeKorLine)) {
            engChangeKorLine = "인천2호선";
        } else if ("dongHaeLine".equals(engChangeKorLine)) {
            engChangeKorLine = "동해선";
        } else if ("buSanGimHae".equals(engChangeKorLine)) {
            engChangeKorLine = "부산김해경전철";
        }

        chatSendRequestDTO.setRegion(engChangeKorRegion);
        chatSendRequestDTO.setLine(engChangeKorLine);

        Long memberNo = Long.parseLong(JwtTokenUtils.getClaimAttribute(authorization, "sequence")); //나 토큰을 가져오겠다. 쓰겠어!

        ChatRegionLineSelectAfterResponseDTO chatRegionLineSelectAfterResponseDTO =
                chatService.selectWebSocketRegionLine(chatSendRequestDTO, memberNo);


        String region = chatSendRequestDTO.getRegion();
        if("수도권".equals(region)){
            region = "seoul";
        } else if ("부산".equals(region)) {
            region = "busan";
        } else if ("대구".equals(region)) {
            region = "daegu";
        } else if ("대전".equals(region)) {
            region = "daejeon";
        } else if ("광주".equals(region)) {
            region = "gwangju";
        }

        String line = chatSendRequestDTO.getLine();

        if ("1호선".equals(line)){
            line = "line1";
        }else if("2호선".equals(line)){
            line = "line2";
        } else if ("3호선".equals(line)) {
            line = "line3";
        }else if ("4호선".equals(line)){
            line = "line4";
        }else if ("5호선".equals(line)){
            line = "line5";
        } else if ("6호선".equals(line)) {
            line = "line6";
        }else if ("7호선".equals(line)){
            line = "line7";
        } else if ("8호선".equals(line)) {
            line = "line8";
        } else if ("9호선".equals(line)) {
            line = "line9";
        } else if ("수인분당선".equals(line)) {
            line = "suInBunDangLine";
        } else if ("신분당선".equals(line)) {
            line = "shinBunDangLine";
        } else if ("경의중앙선".equals(line)) {
            line = "gyeongUiJungAngLine";
        } else if ("경춘선".equals(line)) {
            line = "gyeongChunLine";
        } else if ("경강선".equals(line)) {
            line = "gyeongGangLine";
        } else if ("우의신설선".equals(line)) {
            line = "uiSinSeolLine";
        } else if ("신림선".equals(line)) {
            line = "silLimLine";
        } else if ("김포골드라인".equals(line)) {
            line = "gimPoGoldLine";
        } else if ("에버라인".equals(line)) {
            line = "everLine";
        }else if ("서해선".equals(line)){
            line = "seoHaeLine";
        } else if ("공항철도".equals(line)) {
            line = "gongHangCheolDo";
        } else if ("GTX-A".equals(line)) {
            line = "gtxA";
        } else if ("의정부경전철".equals(line)) {
            line = "uiJeongBu";
        }else if ("인천1호선".equals(line)){
            line = "inCheonLine1";
        } else if ("인천2호선".equals(line)) {
            line = "inCheonLine2";
        } else if ("동해선".equals(line)) {
            line = "dongHaeLine";
        } else if ("부산김해경전철".equals(line)) {
            line = "buSanGimHae";
        }

        chatRegionLineSelectAfterResponseDTO.setChatLine(line);

        // FE 입장에서 받는 부분
        simpMessagingTemplate.convertAndSend("/sub/all/" + region, chatRegionLineSelectAfterResponseDTO);
        simpMessagingTemplate.convertAndSend("/sub/" + region + "/" + line, chatRegionLineSelectAfterResponseDTO);
    }

    // 채팅 삭제
    // 앞에 자동으로 /pub 붙음
    @MessageMapping("/chat-delete")
    public void subChatDelete(ChatDeleteDTO chatDeleteDTO, @Header("Authorization") String authorization){
        Long memberNo = Long.parseLong(JwtTokenUtils.getClaimAttribute(authorization, "sequence"));

        // 실시간 삭제 수신
        simpMessagingTemplate.convertAndSend("/sub/chat-delete" , chatDeleteDTO);

        String engChangeKorRegion = chatDeleteDTO.getRegion();
        if("seoul".equals(engChangeKorRegion)){
            engChangeKorRegion = "수도권";
        } else if ("busan".equals(engChangeKorRegion)) {
            engChangeKorRegion = "부산";
        } else if ("daegu".equals(engChangeKorRegion)) {
            engChangeKorRegion = "대구";
        } else if ("daejeon".equals(engChangeKorRegion)) {
            engChangeKorRegion = "대전";
        } else if ("gwangju".equals(engChangeKorRegion)) {
            engChangeKorRegion = "광주";
        }

        String engChangeKorLine = chatDeleteDTO.getLine();

        if ("line1".equals(engChangeKorLine)){
            engChangeKorLine = "1호선";
        }else if("line2".equals(engChangeKorLine)){
            engChangeKorLine = "2호선";
        } else if ("line3".equals(engChangeKorLine)) {
            engChangeKorLine = "3호선";
        }else if ("line4".equals(engChangeKorLine)){
            engChangeKorLine = "4호선";
        }else if ("line5".equals(engChangeKorLine)){
            engChangeKorLine = "5호선";
        } else if ("line6".equals(engChangeKorLine)) {
            engChangeKorLine = "6호선";
        }else if ("line7".equals(engChangeKorLine)){
            engChangeKorLine = "7호선";
        } else if ("line8".equals(engChangeKorLine)) {
            engChangeKorLine = "8호선";
        } else if ("line9".equals(engChangeKorLine)) {
            engChangeKorLine = "9호선";
        } else if ("suInBunDangLine".equals(engChangeKorLine)) {
            engChangeKorLine = "수인분당선";
        } else if ("shinBunDangLine".equals(engChangeKorLine)) {
            engChangeKorLine = "신분당선";
        } else if ("gyeongUiJungAngLine".equals(engChangeKorLine)) {
            engChangeKorLine = "경의중앙선";
        } else if ("gyeongChunLine".equals(engChangeKorLine)) {
            engChangeKorLine = "경춘선";
        } else if ("gyeongGangLine".equals(engChangeKorLine)) {
            engChangeKorLine = "경강선";
        } else if ("uiSinSeolLine".equals(engChangeKorLine)) {
            engChangeKorLine = "우의신설선";
        } else if ("silLimLine".equals(engChangeKorLine)) {
            engChangeKorLine = "신림선";
        } else if ("gimPoGoldLine".equals(engChangeKorLine)) {
            engChangeKorLine = "김포골드라인";
        } else if ("everLine".equals(engChangeKorLine)) {
            engChangeKorLine = "에버라인";
        }else if ("seoHaeLine".equals(engChangeKorLine)){
            engChangeKorLine = "서해선";
        } else if ("gongHangCheolDo".equals(engChangeKorLine)) {
            engChangeKorLine = "공항철도";
        } else if ("gtxA".equals(engChangeKorLine)) {
            engChangeKorLine = "GTX-A";
        } else if ("uiJeongBu".equals(engChangeKorLine)) {
            engChangeKorLine = "의정부경전철";
        }else if ("inCheonLine1".equals(engChangeKorLine)){
            engChangeKorLine = "인천1호선";
        } else if ("inCheonLine2".equals(engChangeKorLine)) {
            engChangeKorLine = "인천2호선";
        } else if ("dongHaeLine".equals(engChangeKorLine)) {
            engChangeKorLine = "동해선";
        } else if ("buSanGimHae".equals(engChangeKorLine)) {
            engChangeKorLine = "부산김해경전철";
        }

        chatDeleteDTO.setRegion(engChangeKorRegion);
        chatDeleteDTO.setLine(engChangeKorLine);

        chatService.chatDelete(chatDeleteDTO, memberNo);
    }

}
