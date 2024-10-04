package site.nonestep.idontwantwalk.board.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.board.dto.BoardWriteRequestDTO;
import site.nonestep.idontwantwalk.board.dto.BoardWriteResponseDTO;
import site.nonestep.idontwantwalk.board.entity.Board;
import site.nonestep.idontwantwalk.board.repository.BoardRepository;
import site.nonestep.idontwantwalk.member.entity.Member;
import site.nonestep.idontwantwalk.member.repository.MemberRepository;

import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional

public class BoardServiceImpl implements BoardService {
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    //공지게시글 작성
    @Override
    public BoardWriteResponseDTO write(BoardWriteRequestDTO boardWriteRequestDTO, Long memberNo) {
        Member member = memberRepository.getReferenceById(memberNo);//member pk 가지고 올게? . autowired도 해주기

        Board write = boardRepository.save(
                Board.builder()
                        .boardTitle(boardWriteRequestDTO.getBoardTitle())
                        .boardContent(boardWriteRequestDTO.getBoardContent())
                        .boardWriteDate(LocalDateTime.now())
                        .memberNo(member)
                        .build()
        );

        BoardWriteResponseDTO boardWriteResponseDTO = new BoardWriteResponseDTO();
        boardWriteResponseDTO.setBoardNo(write.getBoardNo());
        boardWriteResponseDTO.setBoardWriteDate(write.getBoardWriteDate());

        return boardWriteResponseDTO;
    }
}
