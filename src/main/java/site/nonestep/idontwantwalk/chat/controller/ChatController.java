package site.nonestep.idontwantwalk.chat.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.nonestep.idontwantwalk.chat.service.ChatService;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/chat")//시작하는 url뒤에 공통으로 붙는 키워드
public class ChatController {

    @Autowired
    private ChatService chatService;

}
