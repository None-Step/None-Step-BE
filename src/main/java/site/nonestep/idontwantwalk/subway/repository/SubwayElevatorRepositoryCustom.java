package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.road.dto.GoRoadRequestDTO;
import site.nonestep.idontwantwalk.road.dto.GoStationRequestDTO;
import site.nonestep.idontwantwalk.road.dto.SkResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.InfoElevator;
import site.nonestep.idontwantwalk.subway.dto.SubwayElevatorResponseDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SubwayElevatorRepositoryCustom {

    // 엘리베이터 있는 역 조회
    List<SubwayElevatorResponseDTO> selectElevator(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 1개 정보 전체 조회 시 필요
    List<InfoElevator> selectInfoElevator(String region, String line, String station);

    // 지역, 호선, 역 명을 입력하면 역의 가장 가까운 엘리베이터의 위도, 경도를 return
    Optional<SkResponseDTO> selectNearByElevator(GoStationRequestDTO goStationRequestDTO);

    // [길 찾기: 역 > 목적지] 지역, 역 명을 넣으면 기본 역의 가장 가까운 엘리베이터의 위도, 경도를 return 하는 것
    Optional<SkResponseDTO> selectGoRoadElevator(GoRoadRequestDTO goRoadRequestDTO);
}
