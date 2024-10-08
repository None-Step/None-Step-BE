package site.nonestep.idontwantwalk.bookmark.repository;

import site.nonestep.idontwantwalk.bookmark.dto.PlaceListResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface PlaceMarkRepositoryCustom {

    // [장소] 즐겨 찾기 등록 하기 전, 5개가 넘는지 확인하기 위해 조회
    Long selectPlaceMarkCount(Long memberNo);

    // [장소] 즐겨 찾기 조회
    List<PlaceListResponseDTO> selectPlaceList(Long memberNo);

    // [장소] 즐겨 찾기 삭제
    void deletePlace(Long placeNo);

    // [장소] 동일한 장소를 즐겨 찾기 했는지 조회
    // Count로 조회해서 0이 아니라면 중복으로 등록하려는 경우이기 때문에 null을 return한다.
    Long selectSamePlace (BigDecimal latitude, BigDecimal longitude, Long memberNo);

}
