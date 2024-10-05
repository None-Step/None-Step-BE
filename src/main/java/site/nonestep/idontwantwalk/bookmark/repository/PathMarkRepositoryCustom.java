package site.nonestep.idontwantwalk.bookmark.repository;

import site.nonestep.idontwantwalk.bookmark.dto.PathListResponseDTO;

import java.util.List;

public interface PathMarkRepositoryCustom {

    // [경로] 즐겨 찾기 등록 전, 5개가 넘는지 확인하기 위해 조회
    Long selectPathMarkCount(Long memberNo);

    // [경로] 즐겨 찾기 조회
    List<PathListResponseDTO> selectPathList(Long memberNo);

    // [경로] 즐겨 찾기 삭제
    void deletePath(Long pathNo);
}
