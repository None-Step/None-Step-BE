package site.nonestep.idontwantwalk.bookmark.service;

import site.nonestep.idontwantwalk.bookmark.dto.*;

import java.util.List;

public interface PlaceMarkService {

    // [장소] 즐겨 찾기 등록
    PlaceRegisterResponseDTO placeRegister(PlaceRegisterRequestDTO placeRegisterRequestDTO, Long memberNo);

    // [장소] 즐겨 찾기 조회
    List<PlaceListResponseDTO> placeList(Long memberNo);

    // [장소] 즐겨 찾기 삭제
    PlaceDeleteResponseDTO placeDelete(PlaceDeleteRequestDTO placeDeleteRequestDTO, Long memberNo);

}
