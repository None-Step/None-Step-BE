package site.nonestep.idontwantwalk.board.repository;

import site.nonestep.idontwantwalk.board.entity.Board;

import java.util.List;

public interface ImgRepositoryCustom {

    // 게시글 수정 시 이미지 일괄 삭제 후 다시 삽입
    void deleteBoard(Board boardNo);

    // 게시글 1개 상세 조회 - 이미지 조회
    List<String> findAllBoardDetailImg(Board boardNo);
}
