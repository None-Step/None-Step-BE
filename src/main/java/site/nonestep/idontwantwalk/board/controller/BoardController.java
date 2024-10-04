package site.nonestep.idontwantwalk.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.board.dto.BoardWriteRequestDTO;
import site.nonestep.idontwantwalk.board.dto.BoardWriteResponseDTO;
import site.nonestep.idontwantwalk.board.service.BoardService;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody BoardWriteRequestDTO boardWriteRequestDTO){
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());//토큰을 가져왔다. memberNO 때문

        BoardWriteResponseDTO boardWriteResponseDTO = boardService.write(boardWriteRequestDTO, memberNo);
        return new ResponseEntity<BoardWriteResponseDTO>(boardWriteResponseDTO, HttpStatus.OK);
    }
}
