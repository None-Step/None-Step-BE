package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.subway.dto.InfoNursing;
import site.nonestep.idontwantwalk.subway.dto.SubwayNursingResponseDTO;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayNursingRepositoryCustom {
    // 역 내 수유실 조회
    List<SubwayNursingResponseDTO> selectNursingRoom(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 1개 정보 전체 조회
    List<InfoNursing> selectInfoNursing(String region, String line, String station);
}
