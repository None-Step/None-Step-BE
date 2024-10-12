package site.nonestep.idontwantwalk.subway.repository;

import site.nonestep.idontwantwalk.road.dto.*;
import site.nonestep.idontwantwalk.subway.dto.SubwayClimateCardResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayFloodingResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayLocationResponseDTO;
import site.nonestep.idontwantwalk.subway.dto.SubwayNowResponseDTO;
import site.nonestep.idontwantwalk.subway.entity.Info;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SubwayInfoRepositoryCustom {

    // 위치 기반 역 조회 및 검색
    List<SubwayLocationResponseDTO> selectInfoLatitudeAndInfoLongitudeAndRadius(BigDecimal latitude, BigDecimal longitude, Long radius);

    // 역 1개 정보 전체 조회
    Optional<Info> selectInfo(String region, String line, String station);

    // 지하철 탑승 시 내가 어느 역인지 알아보기
    Optional<SubwayNowResponseDTO> selectStation(BigDecimal latitude, BigDecimal longitude);

    // [도보 길찾기] 지역, 호선을 입력하면 일치하면서 가장 가까운 지하철 기본 역의 위도와 경도를 return
    Optional<SkResponseDTO> selectNearByStation(GoStationRequestDTO goStationRequestDTO);

    // [길 찾기: 역 > 목적지] 지역, 역 명을 넣으면 기본 역의 위도, 경도를 return
    Optional<SkResponseDTO> selectGoRoadStation(GoRoadRequestDTO goRoadRequestDTO);

    // [지하철 경로 찾기] 지역, 호선, 역 명으로 sid, cid 추출
    Optional<SubwayPathResponseDTOX> selectSidAndCid(String region, String line, String station);

    // [목록] 지역, 호선, 역 명을 넣으면 해당 역의 위도, 경도 return
    Optional<StationListDTOX> selectLatitudeAndLongitude(GoListRequestDTO goListRequestDTO);

    // 기후동행카드 승, 하차 지원 여부
    Optional<SubwayClimateCardResponseDTO> selectClimate(String region, String line, String station);

    // 지하철 역 침수 피해 여부 조회
    Optional<SubwayFloodingResponseDTO> selectFlooding(String region, String line, String station);
}
