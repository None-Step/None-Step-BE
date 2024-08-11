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
    @MessageMapping("/chat")
    public void chatSend(ChatSendRequestDTO chatSendRequestDTO, @Header("Authorization") String authorization ){

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

        Long memberNo = Long.parseLong(JwtTokenUtils.getClaimAttribute(authorization, "sequence")); //나 토큰을 가져오겠다. 쓰겠어!

        ChatRegionLineSelectAfterResponseDTO chatRegionLineSelectAfterResponseDTO =
                chatService.selectWebSocketRegionLine(chatSendRequestDTO, memberNo);

        // FE 입장에서 받는 부분
        simpMessagingTemplate.convertAndSend("/sub/all/" + region, chatRegionLineSelectAfterResponseDTO);
        simpMessagingTemplate.convertAndSend("/sub/" + region + "/" + line, chatRegionLineSelectAfterResponseDTO);
    }
}
