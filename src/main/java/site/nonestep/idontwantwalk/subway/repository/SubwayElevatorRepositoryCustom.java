package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.InfoElevator;
import site.nonestep.idontwantwalk.subway.dto.SubwayElevatorResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayElevatorRepositoryCustom {

    // 엘리베이터 있는 역 조회
    List<SubwayElevatorResponseDTO> selectElevator(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 1개 정보 전체 조회 시 필요
    List<InfoElevator> selectInfoElevator(String region, String line, String station);
}
