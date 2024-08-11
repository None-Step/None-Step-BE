package site.nonestep.idontwantwalk.chat.controller;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.chat.dto.*;
import site.nonestep.idontwantwalk.chat.service.ChatService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/chat")//시작하는 url뒤에 공통으로 붙는 키워드
public class ChatController {

    @Autowired
    private ChatService chatService;

    // 지역별 전체 채팅방 조회 - 입장 전
    @GetMapping("/all")
    public ResponseEntity<?> chatAll(@ModelAttribute ChatBeforeAllRequestDTO chatBeforeAllRequestDTO){

        String region = chatBeforeAllRequestDTO.getRegion();
        if("seoul".equals(region)){
            region = "수도권";
        } else if ("busan".equals(region)) {
            region = "부산";
        } else if ("daegu".equals(region)) {
            region = "대구";
        } else if ("daejeon".equals(region)) {
            region = "대전";
        } else if ("gwangju".equals(region)) {
            region = "광주";
        }

        chatBeforeAllRequestDTO.setRegion(region);

        List<ChatBeforeAllResponseDTO> chatBeforeAllResponseDTO = chatService.chatBeforeAll(chatBeforeAllRequestDTO).stream()
                .map(this::ChatBeforeAllChangeLanguage).toList();

        return new ResponseEntity<>(chatBeforeAllResponseDTO, HttpStatus.OK);
    }

    // 호선별 채팅방 조회 - 입장전
    @GetMapping("/subscribe")
    public ResponseEntity<?> subscribeLine(@ModelAttribute ChatBeforeLineRequestDTO chatBeforeLineRequestDTO){

        String region = chatBeforeLineRequestDTO.getRegion();
        if("seoul".equals(region)){
            region = "수도권";
        } else if ("busan".equals(region)) {
            region = "부산";
        } else if ("daegu".equals(region)) {
            region = "대구";
        } else if ("daejeon".equals(region)) {
            region = "대전";
        } else if ("gwangju".equals(region)) {
            region = "광주";
        }

        String line = chatBeforeLineRequestDTO.getLine();

        if ("line1".equals(line)){
            line = "1호선";
        }else if("line2".equals(line)){
            line = "2호선";
        } else if ("line3".equals(line)) {
            line = "3호선";
        }else if ("line4".equals(line)){
            line = "4호선";
        }else if ("line5".equals(line)){
            line = "5호선";
        } else if ("line6".equals(line)) {
            line = "6호선";
        }else if ("line7".equals(line)){
            line = "7호선";
        } else if ("line8".equals(line)) {
            line = "8호선";
        } else if ("line9".equals(line)) {
            line = "9호선";
        } else if ("suInBunDangLine".equals(line)) {
            line = "수인분당선";
        } else if ("shinBunDangLine".equals(line)) {
            line = "신분당선";
        } else if ("gyeongUiJungAngLine".equals(line)) {
            line = "경의중앙선";
        } else if ("gyeongChunLine".equals(line)) {
            line = "경춘선";
        } else if ("gyeongGangLine".equals(line)) {
            line = "경강선";
        } else if ("uiSinSeolLine".equals(line)) {
            line = "우의신설선";
        } else if ("silLimLine".equals(line)) {
            line = "신림선";
        } else if ("gimPoGoldLine".equals(line)) {
            line = "김포골드라인";
        } else if ("everLine".equals(line)) {
            line = "에버라인";
        }else if ("seoHaeLine".equals(line)){
            line = "서해선";
        } else if ("gongHangCheolDo".equals(line)) {
            line = "공항철도";
        } else if ("gtxA".equals(line)) {
            line = "GTX-A";
        } else if ("uiJeongBu".equals(line)) {
            line = "의정부경전철";
        }else if ("inCheonLine1".equals(line)){
            line = "인천1호선";
        } else if ("inCheonLine2".equals(line)) {
            line = "인천2호선";
        } else if ("dongHaeLine".equals(line)) {
            line = "동해선";
        } else if ("buSanGimHae".equals(line)) {
            line = "부산김해경전철";
        }

        chatBeforeLineRequestDTO.setRegion(region);
        chatBeforeLineRequestDTO.setLine(line);

        List<ChatBeforeLineResponseDTO> chatBeforeLine = chatService.chatBeforeLine(chatBeforeLineRequestDTO);

        return new ResponseEntity<>(chatBeforeLine, HttpStatus.OK);
    }

    // 호선(한글) > 영어로
    public ChatBeforeAllResponseDTO ChatBeforeAllChangeLanguage (ChatBeforeAllResponseDTO chatBeforeAllResponseDTO){

        String line = chatBeforeAllResponseDTO.getChatLine();

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

        chatBeforeAllResponseDTO.setChatLine(line);

        return chatBeforeAllResponseDTO;
    }

    // 호선(한글) > 영어로
    public ChatListResponseDTO ChangeKorToEng (ChatListResponseDTO chatListResponseDTO){

        String line = chatListResponseDTO.getLine();

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

        chatListResponseDTO.setLine(line);

        return chatListResponseDTO;
    }


    // 채팅 목록 보기
    @GetMapping("/list")
    public ResponseEntity<?> chatList (@ModelAttribute ChatListRequestDTO chatListRequestDTO){

        String region = chatListRequestDTO.getRegion();
        if("seoul".equals(region)){
            region = "수도권";
        } else if ("busan".equals(region)) {
            region = "부산";
        } else if ("daegu".equals(region)) {
            region = "대구";
        } else if ("daejeon".equals(region)) {
            region = "대전";
        } else if ("gwangju".equals(region)) {
            region = "광주";
        }

        chatListRequestDTO.setRegion(region);

        List<ChatListResponseDTO> chatListResponseDTO = chatService.chatList(chatListRequestDTO).stream()
                .map(this::ChangeKorToEng).toList();
        return new ResponseEntity<>(chatListResponseDTO, HttpStatus.OK);
    }

}
