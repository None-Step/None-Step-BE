package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.SubwayATMResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayATMRepositoryCustom {
    // 역 내 atm 조회
    List<SubwayATMResponseDTO> selectATM(BigDecimal latitude, BigDecimal longitude, Long radius);
}
