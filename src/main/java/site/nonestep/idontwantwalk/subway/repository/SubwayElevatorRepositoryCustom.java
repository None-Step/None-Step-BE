package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.SubwayElevatorResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayElevatorRepositoryCustom {

    // 엘리베이터 있는 역 조회
    List<SubwayElevatorResponseDTO> selectElevator(BigDecimal latitude, BigDecimal longitude, Long radius);
}
