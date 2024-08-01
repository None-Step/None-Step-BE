package site.nonestep.idontwantwalk.subway.service;

import site.nonestep.idontwantwalk.subway.dto.*;

import java.math.BigDecimal;
import java.util.List;

public interface SubwayService {

    // 위치 기반 역 조회 및 검색
    List<SubwayLocationResponseDTO> location(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 엘리베이터 있는 역 조회
    List<SubwayElevatorResponseDTO> elevator(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 에스컬레이터 있는 역 조회
    List<SubwayEscalResponseDTO> escal(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 휠체어 리프트 있는 역 조회
    List<SubwayLiftResponseDTO> lift(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 내 화장실 조회
    List<SubwayToiletResponseDTO> toilet(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 내 장애인 화장실 조회
    List<SubwayDifToiletResponseDTO> difToilet(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 내 수유실 조회
    List<SubwayNursingResponseDTO> nursingRoom(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 내 atm 조회
    List<SubwayATMResponseDTO> atm(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 내 제세동기 조회(aed)
    List<SubwayAEDResponseDTO> aed(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 내 전동 휠체어 충전 설비 조회
    List<SubwayChargerResponseDTO> charger(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 내 고객센터 조회
    List<SubwayCenterResponseDTO> center(BigDecimal latitude, BigDecimal longitue, Long radius);
}
