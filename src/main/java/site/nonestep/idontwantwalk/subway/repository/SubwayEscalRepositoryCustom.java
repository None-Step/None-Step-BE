package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.SubwayEscalResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayEscalRepositoryCustom {

    // 에스컬레이터 있는 역 조회
    List<SubwayEscalResponseDTO> selectEscal(BigDecimal latitude, BigDecimal longitude, Long radius);
}
