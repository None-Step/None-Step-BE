package site.nonestep.idontwantwalk.bookmark.service;

import site.nonestep.idontwantwalk.bookmark.dto.PlaceRegisterRequestDTO;
import site.nonestep.idontwantwalk.bookmark.dto.PlaceRegisterResponseDTO;

public interface PlaceMarkService {

    // [장소] 즐겨 찾기 등록
    PlaceRegisterResponseDTO placeRegister(PlaceRegisterRequestDTO placeRegisterRequestDTO, Long memberNo);
}
