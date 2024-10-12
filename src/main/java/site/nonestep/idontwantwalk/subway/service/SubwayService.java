package site.nonestep.idontwantwalk.subway.service;

import site.nonestep.idontwantwalk.road.dto.*;
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

    // 역 1개 정보 전체 조회
    SubwayStationInfoResponseDTO totalStationInfo(SubwayStationInfoRequestDTO subwayStationInfoRequestDTO);

    // [길 찾기: 내 위치 > 역] 지역, 역명을 넣으면 기본 역의 위도, 경도를 return해주는 것
    SkResponseDTO walkStation(GoStationRequestDTO goStationRequestDTO);

    // [길 찾기: 내 위치 > 역] 지역, 역명을 넣으면 기본 역의 가장 가까운 에스컬레이터의 위도, 경도를 return하는 것
    SkResponseDTO nearByEscal(GoStationRequestDTO goStationRequestDTO);

    // [길 찾기: 내 위치 > 역] 지역, 역명을 넣으면 기본 역의 가장 가까운 엘리베이터의 위도, 경도를 return 하는 것
    SkResponseDTO nearByElevator(GoStationRequestDTO goStationRequestDTO);

    // 지하철 탑승 시, 내가 어느 역인지 알아보기
    SubwayNowResponseDTO nowStation(SubwayNowRequestDTO subwayNowRequestDTO);

    // [길 찾기: 역 > 목적지] 지역, 역 명을 넣으면 기본 역의 위도, 경도를 return
    SkResponseDTO goRoadStation(GoRoadRequestDTO goRoadRequestDTO);

    // [길 찾기: 역 > 목적지] 지역, 역 명을 넣으면 기본 역의 가장 가까운 에스컬레이터의 위도, 경도를 return 하는 것
    SkResponseDTO goRoadEscal(GoRoadRequestDTO goRoadRequestDTO);

    // [길 찾기: 역 > 목적지] 지역, 역 명을 넣으면 기본 역의 가장 가까운 엘리베이터의 위도, 경도를 return 하는 것
    SkResponseDTO goRoadElevator(GoRoadRequestDTO goRoadRequestDTO);

    // [목록] 지역, 호선, 역 명을 넣으면 해당 역의 위도, 경도 return
    StationListDTOX station(GoListRequestDTO goListRequestDTO);

    // 상행선 시간표 조회
    List<SubwayUpTimeResponseDTO> upTime(SubwayUpTimeRequestDTO subwayUpTimeRequestDTO);

    // 하행선 시간표 조회
    List<SubwayDownTimeResponseDTO> downTime(SubwayDownTimeRequestDTO subwayDownTimeRequestDTO);

    // 기후동행카드 승, 하차 지원 여부
    SubwayClimateCardResponseDTO climateCard(SubwayClimateCardRequestDTO subwayClimateCardRequestDTO);

    // 지하철 역 침수 피해 여부
    SubwayFloodingResponseDTO flooding(SubwayFloodingRequestDTO subwayFloodingRequestDTO);
}
