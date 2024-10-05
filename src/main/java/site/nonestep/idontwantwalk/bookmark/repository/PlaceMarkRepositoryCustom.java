package site.nonestep.idontwantwalk.bookmark.repository;

import site.nonestep.idontwantwalk.bookmark.dto.PlaceListResponseDTO;

import java.util.List;

public interface PlaceMarkRepositoryCustom {

    // [장소] 즐겨 찾기 등록 하기 전, 5개가 넘는지 확인하기 위해 조회
    Long selectPlaceMarkCount(Long memberNo);

    // [장소] 즐겨 찾기 조회
    List<PlaceListResponseDTO> selectPlaceList(Long memberNo);

    // [장소] 즐겨 찾기 삭제
    void deletePlace(Long placeNo);

}
