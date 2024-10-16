package site.nonestep.idontwantwalk.board.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.board.dto.*;
import site.nonestep.idontwantwalk.board.service.BoardService;
import site.nonestep.idontwantwalk.board.service.ImgService;
import site.nonestep.idontwantwalk.member.service.S3UploadService;

import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/board")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @Autowired
    private S3UploadService s3UploadService;

    @Autowired
    private ImgService imgService;


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

    //게시글 삭제
    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@ModelAttribute BoardDeleteRequestDTO boardDeleteRequestDTO) {
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        BoardDeleteResponseDTO boardDeleteResponseDTO = boardService.boardDelete(boardDeleteRequestDTO, memberNo);

        return new ResponseEntity<>(boardDeleteResponseDTO, HttpStatus.OK);

    }

    ;

    //게시글 상세조회
    @GetMapping("/detail")
    public ResponseEntity<?> detail(@ModelAttribute BoardDetailRequestDTO boardDetailRequestDTO) {
        BoardDetailResponseDTO boardDetailResponseDTO = boardService.boardDetail(boardDetailRequestDTO);

        return new ResponseEntity<>(boardDetailResponseDTO, HttpStatus.OK);
    }

    // 이미지 보내기
    @PostMapping("/img")
    public ResponseEntity<?> postImg(@ModelAttribute ImgRequestDTO imgRequestDTO){

        // 이미지를 s3를 거쳐서 보낸다
        String postImg = s3UploadService.upload(imgRequestDTO.getImg(), "nonestepBoardImg");

        // 그걸 다시 responsebody에 담아서 보낸다.
        ImgResponseDTO imgResponseDTO = new ImgResponseDTO();
        imgResponseDTO.setImg(postImg);


        return new ResponseEntity<>(imgResponseDTO, HttpStatus.OK);
    }

    //게시글이 몇 페이지까지 있는지
    @GetMapping("/page")
    public ResponseEntity<?> page(){
        BoardPageResponseDTO boardPageResponseDTO = boardService.selectBoardPage();

        return new ResponseEntity<>(boardPageResponseDTO, HttpStatus.OK);
    }

    //목록 조회 최상단
    @GetMapping("/main-notice")
    public ResponseEntity<?> mainNotice(){
        BoardMainNoticeResponseDTO boardMainNoticeResponseDTO = boardService.boardMainNotice();
        return new ResponseEntity<>(boardMainNoticeResponseDTO,HttpStatus.OK);
    }

    //게시글 검색
    @PostMapping("/search")
    public ResponseEntity<?> search(@RequestBody BoardSearchRequestDTO boardSearchRequestDTO){

        List<BoardSearchResponseDTO> boardSearchResponseDTO = boardService.boardSearch(boardSearchRequestDTO);
        return new ResponseEntity<>(boardSearchResponseDTO, HttpStatus.OK);
    }

}
