package site.nonestep.idontwantwalk.bookmark.service;

import site.nonestep.idontwantwalk.bookmark.dto.*;

import java.util.List;

public interface PathMarkService {

    // [경로] 즐겨 찾기 등록
    PathRegisterResponseDTO pathRegister(PathRegisterRequestDTO pathRegisterRequestDTO, Long memberNo) throws Exception;

    // [경로] 즐겨 찾기 조회
    List<PathListResponseDTO> pathList(Long memberNo);

    // [장소] 즐겨 찾기 삭제
    PathDeleteResponseDTO pathDelete(PathDeleteRequestDTO pathDeleteRequestDTO, Long memberNo);
}
