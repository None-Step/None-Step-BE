package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.InfoCenter;
import site.nonestep.idontwantwalk.subway.dto.SubwayCenterResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayCenterRepositoryCustom {
    // 역 내 고객센터 조회
    List<SubwayCenterResponseDTO> selectCenter(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 1개 정보 전체 조회
    List<InfoCenter> selectInfoCenter(String region, String line, String station);
}
