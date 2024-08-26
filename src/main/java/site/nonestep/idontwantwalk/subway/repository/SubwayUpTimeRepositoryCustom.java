package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.SubwayUpTimeRequestDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayUpTimeResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.UpTime;

import java.util.List;

public interface SubwayUpTimeRepositoryCustom {

    // 지역, 호선, 역 명이 같으면 시간표 전체 조회
    List<SubwayUpTimeResponseDTO> selectUpTime(SubwayUpTimeRequestDTO subwayUpTimeRequestDTO);
}
