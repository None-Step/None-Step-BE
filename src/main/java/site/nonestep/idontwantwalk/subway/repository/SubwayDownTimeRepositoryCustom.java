package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.SubwayDownTimeRequestDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayDownTimeResponseDTO;

import java.util.List;

public interface SubwayDownTimeRepositoryCustom {
    // 지역, 호선, 역 명이 같으면 시간표 전체 조회
    List<SubwayDownTimeResponseDTO> selectDownTime(SubwayDownTimeRequestDTO subwayDownTimeRequestDTO);
}
