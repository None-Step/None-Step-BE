package site.nonestep.idontwantwalk.bookmark.service;

import site.nonestep.idontwantwalk.bookmark.dto.*;

import java.util.List;

public interface SubwayMarkService {

    // [지하철 역] 즐겨 찾기 등록
    SubwayRegisterResponseDTO subwayRegister(SubwayRegisterRequestDTO subwayRegisterRequestDTO, Long memberNo);

    // [지하철 역] 즐겨 찾기 조회
    List<SubwayListResponseDTO> subwayList(Long MemberNo);

    // [지하철 역] 즐겨 찾기 삭제
    SubwayDeleteResponseDTO subwayDelete(SubwayDeleteRequestDTO subwayDeleteRequestDTO, Long memberNo);
}
