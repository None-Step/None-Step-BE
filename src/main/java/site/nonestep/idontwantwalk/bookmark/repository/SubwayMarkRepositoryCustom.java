package site.nonestep.idontwantwalk.bookmark.repository;

import site.nonestep.idontwantwalk.bookmark.dto.SubwayDeleteResponseDTO;
import site.nonestep.idontwantwalk.bookmark.dto.SubwayListResponseDTO;
import site.nonestep.idontwantwalk.bookmark.entity.SubwayMark;

import java.util.List;
import java.util.Optional;

public interface SubwayMarkRepositoryCustom {

    // [지하철 역] 즐겨 찾기 등록 전, 갯수 초과가 아닌지 조회
    Long selectSubwayMarkCount(Long memberNo);

    // [지하철 역] 동일한 즐겨 찾기가 있는지 조회
    // Count로 조회해서 0이 아니라면 중복으로 등록하려는 경우이기 때문에 null을 return한다.
    Long selectSameSubway(String region, String line, String station, Long memberNo);

    // [지하철 역] 즐겨 찾기 조회
    List<SubwayListResponseDTO> selectSubwayMark(Long memberNo);

    // [지하철 역] 즐겨 찾기 삭제
    void deleteSubwayMark(String region, String line, String station, Long memberNo);
}
