package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.SubwayAEDResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.Subway;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayAedRepositoryCustom {
    // 역 내 AED 조회
    List<SubwayAEDResponseDTO> selectAED(BigDecimal latitude, BigDecimal longitude, Long radius);
}
