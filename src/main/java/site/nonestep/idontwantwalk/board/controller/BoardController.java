package site.nonestep.idontwantwalk.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.board.dto.*;
import site.nonestep.idontwantwalk.board.service.BoardService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    //공지 게시물 작성
    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody BoardWriteRequestDTO boardWriteRequestDTO) {
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());//토큰을 가져왔다. memberNO 때문

        BoardWriteResponseDTO boardWriteResponseDTO = boardService.write(boardWriteRequestDTO, memberNo);
        return new ResponseEntity<BoardWriteResponseDTO>(boardWriteResponseDTO, HttpStatus.OK);
    }

    //공지 게시물 조회
    @GetMapping("/list")
    public ResponseEntity<?> list(@ModelAttribute BoardListRequestDTO boardListRequestDTO) {

        if (boardListRequestDTO.getPage() <= 1) {
            boardListRequestDTO.setPage(0L);
        } else {
            boardListRequestDTO.setPage(boardListRequestDTO.getPage() - 1);
        }
        List<BoardListResponseDTO> boardListResponseDTO = boardService.boardList(boardListRequestDTO);
        return new ResponseEntity<>(boardListResponseDTO, HttpStatus.OK);

    }

    //게시글 수정
    @PutMapping("/modify")
    public ResponseEntity<?> modify(@RequestBody BoardModifyRequestDTO boardModifyRequestDTO) {
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        BoardModifyResponseDTO boardModifyResponseDTO = boardService.boardModify(boardModifyRequestDTO, memberNo);

        return new ResponseEntity<>(boardModifyResponseDTO, HttpStatus.OK);
    }
}
