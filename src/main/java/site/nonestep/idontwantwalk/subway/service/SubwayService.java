package site.nonestep.idontwantwalk.subway.service;

import site.nonestep.idontwantwalk.subway.dto.SubwayElevatorResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayEscalResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayLiftResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayLocationResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayService {

    // 위치 기반 역 조회 및 검색
    List<SubwayLocationResponseDTO> location(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 엘리베이터 있는 역 조회
    List<SubwayElevatorResponseDTO> elevator(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 에스컬레이터 있는 역 조회
    List<SubwayEscalResponseDTO> escal(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 휠체어 리프트 있는 역 조회
    List<SubwayLiftResponseDTO> lift(BigDecimal latitude, BigDecimal longitude, Long radius);
}
