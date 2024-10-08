package site.nonestep.idontwantwalk.bookmark.repository;

import site.nonestep.idontwantwalk.bookmark.dto.PathListResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface PathMarkRepositoryCustom {

    // [경로] 즐겨 찾기 등록 전, 5개가 넘는지 확인하기 위해 조회
    Long selectPathMarkCount(Long memberNo);

    // [경로] 즐겨 찾기 조회
    List<PathListResponseDTO> selectPathList(Long memberNo);

    // [경로] 즐겨 찾기 삭제
    void deletePath(Long pathNo);

    // [경로] 동일한 즐겨 찾기가 존재하는지 조회
    // Count로 조회해서 0이 아니라면 중복으로 등록하려는 경우이기 때문에 null을 return한다.
    Long selectSamePath (BigDecimal startLatitude, BigDecimal startLongitude, BigDecimal endLatitude, BigDecimal endLongitude, Long memberNo);
}
