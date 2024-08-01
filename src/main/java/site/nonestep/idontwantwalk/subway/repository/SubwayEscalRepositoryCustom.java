package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.InfoEscal;
import site.nonestep.idontwantwalk.subway.dto.SubwayEscalResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayEscalRepositoryCustom {

    // 에스컬레이터 있는 역 조회
    List<SubwayEscalResponseDTO> selectEscal(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 1개 정보 전체 조회할 때 쓸 코드
    List<InfoEscal> selectEscal(String region, String line, String station);
}
