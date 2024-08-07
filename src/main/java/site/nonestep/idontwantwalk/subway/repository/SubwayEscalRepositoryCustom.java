package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.road.dto.GoStationRequestDTO;
import site.nonestep.idontwantwalk.road.dto.SkResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.InfoEscal;
import site.nonestep.idontwantwalk.subway.dto.SubwayEscalResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.Escal;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SubwayEscalRepositoryCustom {

    // 에스컬레이터 있는 역 조회
    List<SubwayEscalResponseDTO> selectEscal(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 1개 정보 전체 조회할 때 쓸 코드
    List<InfoEscal> selectEscal(String region, String line, String station);

    // 지역, 호선, 역 명을 입력하면 역의 가장 가까운 에스컬레이터의 위도, 경도를 return
    Optional<SkResponseDTO> selectNearByEscal(GoStationRequestDTO goStationRequestDTO);
}
