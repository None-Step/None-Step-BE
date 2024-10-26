package site.nonestep.idontwantwalk.congestion.service;

import com.querydsl.core.Tuple;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.congestion.dto.*;
import site.nonestep.idontwantwalk.congestion.entity.CarCongestion;
import site.nonestep.idontwantwalk.congestion.entity.DownCongestion;
import site.nonestep.idontwantwalk.congestion.entity.UpCongestion;
import site.nonestep.idontwantwalk.congestion.repository.*;
import site.nonestep.idontwantwalk.subway.entity.Info;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import static site.nonestep.idontwantwalk.congestion.entity.QDownCongestion.downCongestion;
import static site.nonestep.idontwantwalk.congestion.entity.QUpCongestion.upCongestion;
import static site.nonestep.idontwantwalk.subway.entity.QInfo.info;

@Slf4j
@Service
@Transactional
public class CongestionServiceImpl implements CongestionService {
    @Autowired
    private UpCongestionRepository upCongestionRepository;

    @Autowired
    private DownCongestionRepository downCongestionRepository;

    @Autowired
    private UpEtcRepository upEtcRepository;

    @Autowired
    private DownEtcRepository downEtcRepository;

    @Autowired
    private CarCongestionRepository carCongestionRepository;


    // 해당 역의 상행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤
    // 어떤 시간이든 30분 단위로 나눔 그럼 나머지가 사라짐 그 후, 30을 곱함
    // ex. 1031 입력 > 1030 data 출력 , 1100 입력 > 1100 data 출력
    // 해당 역의 data가 없을 경우 return null 하지 않고, 그냥 빈 ResponseDTO를 보낸다.
    // Controller에서 null일 경우 BAD_REQUEST를 보내기 때문이다.
    // 그 외의 오류를 대비하기 위해서 Controller는 그냥 둔다.(null이 들어가지 않게끔 짰는데도 null이 들어갈 경우를 대비해서)
    @Override
    public UpTimeResponseDTO upTime(UpTimeRequestDTO upTimeRequestDTO) {
        UpCongestion up = upCongestionRepository.selectCurrentTimeTo1Hours(upTimeRequestDTO.getRegion(), upTimeRequestDTO.getLine(),
                upTimeRequestDTO.getStation(), upTimeRequestDTO.getType());

        if (up == null) {
            UpTimeResponseDTO upTimeResponseDTO = new UpTimeResponseDTO();

            return upTimeResponseDTO;
        } else {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

            Duration unit = Duration.ofMinutes(30);

            LocalTime start = LocalTime.of(0, 0);
            LocalTime currentTime = LocalTime.parse(upTimeRequestDTO.getTime(), timeFormatter);
            currentTime = currentTime.minus(30, ChronoUnit.MINUTES);

            long howMany = Duration.between(start, currentTime).dividedBy(unit);
            LocalTime requireTime = start.plus(howMany * 30, ChronoUnit.MINUTES);

            UpTimeResponseDTO upTimeResponseDTO = new UpTimeResponseDTO();
            requireTime = requireTime.plus(30, ChronoUnit.MINUTES);
            upTimeResponseDTO.setCurrentTime(localTimeToData(requireTime, up));
            requireTime = requireTime.plus(30, ChronoUnit.MINUTES);
            upTimeResponseDTO.setAfter30(localTimeToData(requireTime, up));
            requireTime = requireTime.plus(30, ChronoUnit.MINUTES);
            upTimeResponseDTO.setAfter60(localTimeToData(requireTime, up));
            upTimeResponseDTO.setNextStation(up.getUpNextStation());

            return upTimeResponseDTO;
        }
    }

    // 해당 역의 하행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤
    // 어떤 시간이든 30분 단위로 나눔 그럼 나머지가 사라짐 그 후, 30을 곱함
    // ex. 1031 입력 > 1030 data 출력 , 1100 입력 > 1100 data 출력
    // 해당 역의 data가 없을 경우 return null 하지 않고, 그냥 빈 ResponseDTO를 보낸다.
    // Controller에서 null일 경우 BAD_REQUEST를 보내기 때문이다.
    // 그 외의 오류를 대비하기 위해서 Controller는 그냥 둔다.(null이 들어가지 않게끔 짰는데도 null이 들어갈 경우를 대비해서)
    @Override
    public DownTimeResponseDTO downTime(DownTimeRequestDTO downTimeRequestDTO) {
        DownCongestion down = downCongestionRepository.selectCurrentTimeTo1Hours(downTimeRequestDTO.getRegion(),
                downTimeRequestDTO.getLine(), downTimeRequestDTO.getStation(), downTimeRequestDTO.getType());

        if (down == null) {
            DownTimeResponseDTO downTimeResponseDTO = new DownTimeResponseDTO();

            return downTimeResponseDTO;
        } else {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

            Duration unit = Duration.ofMinutes(30);

            LocalTime start = LocalTime.of(0, 0);
            LocalTime currentTime = LocalTime.parse(downTimeRequestDTO.getTime(), timeFormatter);
            currentTime = currentTime.minus(30, ChronoUnit.MINUTES);

            long howMany = Duration.between(start, currentTime).dividedBy(unit);
            LocalTime requireTime = start.plus(howMany * 30, ChronoUnit.MINUTES);

            DownTimeResponseDTO downTimeResponseDTO = new DownTimeResponseDTO();
            requireTime = requireTime.plus(30, ChronoUnit.MINUTES);
            downTimeResponseDTO.setCurrentTime(localTimeToData(requireTime, down));
            requireTime = requireTime.plus(30, ChronoUnit.MINUTES);
            downTimeResponseDTO.setAfter30(localTimeToData(requireTime, down));
            requireTime = requireTime.plus(30, ChronoUnit.MINUTES);
            downTimeResponseDTO.setAfter60(localTimeToData(requireTime, down));
            downTimeResponseDTO.setNextStation(down.getDownNextStation());

            return downTimeResponseDTO;
        }

    }

    // 시간단위를 HHmm으로 설정
    // 원하는 시간에 맞춰 data 출력하기 위한 함수 (상행선 용)
    private String localTimeToData(LocalTime time, UpCongestion upCongestion) {

        String localTimeStr = time.format(DateTimeFormatter.ofPattern("HHmm"));

        switch (localTimeStr) {
            case "0530":
                return congestionToState(upCongestion.getUpCongestion0530());
            case "0600":
                return congestionToState(upCongestion.getUpCongestion0600());
            case "0630":
                return congestionToState(upCongestion.getUpCongestion0630());
            case "0700":
                return congestionToState(upCongestion.getUpCongestion0700());
            case "0730":
                return congestionToState(upCongestion.getUpCongestion0730());
            case "0800":
                return congestionToState(upCongestion.getUpCongestion0800());
            case "0830":
                return congestionToState(upCongestion.getUpCongestion0830());
            case "0900":
                return congestionToState(upCongestion.getUpCongestion0900());
            case "0930":
                return congestionToState(upCongestion.getUpCongestion0930());
            case "1000":
                return congestionToState(upCongestion.getUpCongestion1000());
            case "1030":
                return congestionToState(upCongestion.getUpCongestion1030());
            case "1100":
                return congestionToState(upCongestion.getUpCongestion1100());
            case "1130":
                return congestionToState(upCongestion.getUpCongestion1130());
            case "1200":
                return congestionToState(upCongestion.getUpCongestion1200());
            case "1230":
                return congestionToState(upCongestion.getUpCongestion1230());
            case "1300":
                return congestionToState(upCongestion.getUpCongestion1300());
            case "1330":
                return congestionToState(upCongestion.getUpCongestion1330());
            case "1400":
                return congestionToState(upCongestion.getUpCongestion1400());
            case "1430":
                return congestionToState(upCongestion.getUpCongestion1430());
            case "1500":
                return congestionToState(upCongestion.getUpCongestion1500());
            case "1530":
                return congestionToState(upCongestion.getUpCongestion1530());
            case "1600":
                return congestionToState(upCongestion.getUpCongestion1600());
            case "1630":
                return congestionToState(upCongestion.getUpCongestion1630());
            case "1700":
                return congestionToState(upCongestion.getUpCongestion1700());
            case "1730":
                return congestionToState(upCongestion.getUpCongestion1730());
            case "1800":
                return congestionToState(upCongestion.getUpCongestion1800());
            case "1830":
                return congestionToState(upCongestion.getUpCongestion1830());
            case "1900":
                return congestionToState(upCongestion.getUpCongestion1900());
            case "1930":
                return congestionToState(upCongestion.getUpCongestion1930());
            case "2000":
                return congestionToState(upCongestion.getUpCongestion2000());
            case "2030":
                return congestionToState(upCongestion.getUpCongestion2030());
            case "2100":
                return congestionToState(upCongestion.getUpCongestion2100());
            case "2130":
                return congestionToState(upCongestion.getUpCongestion2130());
            case "2200":
                return congestionToState(upCongestion.getUpCongestion2200());
            case "2230":
                return congestionToState(upCongestion.getUpCongestion2230());
            case "2300":
                return congestionToState(upCongestion.getUpCongestion2300());
            case "2330":
                return congestionToState(upCongestion.getUpCongestion2330());
            case "0000":
                return congestionToState(upCongestion.getUpCongestion0000());
            case "0030":
                return congestionToState(upCongestion.getUpCongestion0030());
        }

        return "";
    }

    // 시간단위를 HHmm으로 설정
    // 원하는 시간에 맞춰 data 출력하기 위한 함수 (하행선 용)
    private String localTimeToData(LocalTime time, DownCongestion downCongestion) {

        String localTimeStr = time.format(DateTimeFormatter.ofPattern("HHmm"));

        switch (localTimeStr) {
            case "0530":
                return congestionToState(downCongestion.getDownCongestion0530());
            case "0600":
                return congestionToState(downCongestion.getDownCongestion0600());
            case "0630":
                return congestionToState(downCongestion.getDownCongestion0630());
            case "0700":
                return congestionToState(downCongestion.getDownCongestion0700());
            case "0730":
                return congestionToState(downCongestion.getDownCongestion0730());
            case "0800":
                return congestionToState(downCongestion.getDownCongestion0800());
            case "0830":
                return congestionToState(downCongestion.getDownCongestion0830());
            case "0900":
                return congestionToState(downCongestion.getDownCongestion0900());
            case "0930":
                return congestionToState(downCongestion.getDownCongestion0930());
            case "1000":
                return congestionToState(downCongestion.getDownCongestion1000());
            case "1030":
                return congestionToState(downCongestion.getDownCongestion1030());
            case "1100":
                return congestionToState(downCongestion.getDownCongestion1100());
            case "1130":
                return congestionToState(downCongestion.getDownCongestion1130());
            case "1200":
                return congestionToState(downCongestion.getDownCongestion1200());
            case "1230":
                return congestionToState(downCongestion.getDownCongestion1230());
            case "1300":
                return congestionToState(downCongestion.getDownCongestion1300());
            case "1330":
                return congestionToState(downCongestion.getDownCongestion1330());
            case "1400":
                return congestionToState(downCongestion.getDownCongestion1400());
            case "1430":
                return congestionToState(downCongestion.getDownCongestion1430());
            case "1500":
                return congestionToState(downCongestion.getDownCongestion1500());
            case "1530":
                return congestionToState(downCongestion.getDownCongestion1530());
            case "1600":
                return congestionToState(downCongestion.getDownCongestion1600());
            case "1630":
                return congestionToState(downCongestion.getDownCongestion1630());
            case "1700":
                return congestionToState(downCongestion.getDownCongestion1700());
            case "1730":
                return congestionToState(downCongestion.getDownCongestion1730());
            case "1800":
                return congestionToState(downCongestion.getDownCongestion1800());
            case "1830":
                return congestionToState(downCongestion.getDownCongestion1830());
            case "1900":
                return congestionToState(downCongestion.getDownCongestion1900());
            case "1930":
                return congestionToState(downCongestion.getDownCongestion1930());
            case "2000":
                return congestionToState(downCongestion.getDownCongestion2000());
            case "2030":
                return congestionToState(downCongestion.getDownCongestion2030());
            case "2100":
                return congestionToState(downCongestion.getDownCongestion2100());
            case "2130":
                return congestionToState(downCongestion.getDownCongestion2130());
            case "2200":
                return congestionToState(downCongestion.getDownCongestion2200());
            case "2230":
                return congestionToState(downCongestion.getDownCongestion2230());
            case "2300":
                return congestionToState(downCongestion.getDownCongestion2300());
            case "2330":
                return congestionToState(downCongestion.getDownCongestion2330());
            case "0000":
                return congestionToState(downCongestion.getDownCongestion0000());
            case "0030":
                return congestionToState(downCongestion.getDownCongestion0030());
        }

        return "";
    }

    // 출력한 data 값을 아래 if문의 조건에 따라 여유, 보통, 주의, 혼잡 단계로 나눠 return
    public String congestionToState(BigDecimal num) {

        // 정보가 없는 역들도 존재하기 때문에, null인 경우를 크게 감싼다.
        if (num == null) {
            return "정보없음";
        } else {

            int spare = num.compareTo(BigDecimal.valueOf(29));
            int normal = num.compareTo(BigDecimal.valueOf(49));
            int caution = num.compareTo(BigDecimal.valueOf(79));

            if (spare < 0) {
                return "여유";
            } else if (normal < 0) {
                return "보통";
            } else if (caution < 0) {
                return "주의";
            } else {
                return "혼잡";
            }
        }
    }

    // 해당 역의 상행선 이격거리 및 추가 정보
    @Override
    public List<UpInfoResponseDTO> upInfo(UpInfoRequestDTO upInfoRequestDTO) {
        List<UpInfoResponseDTO> selectUpInfo = upEtcRepository.selectUpInfo(upInfoRequestDTO.getRegion(),
                upInfoRequestDTO.getLine(), upInfoRequestDTO.getStation());

        return selectUpInfo;
    }

    // 해당 역의 하행선 이격거리 및 추가 정보
    @Override
    public List<DownInfoResponseDTO> downInfo(DownInfoRequestDTO downInfoRequestDTO) {

        List<DownInfoResponseDTO> selectDownInfo = downEtcRepository.selectDownInfo(downInfoRequestDTO.getRegion(),
                downInfoRequestDTO.getLine(), downInfoRequestDTO.getStation());

        return selectDownInfo;
    }

    // 역 혼잡도 - 마커용
    @Override
    public List<SubwayMarkerResponseDTO> subwayMark(SubwayMarkerRequestDTO subwayMarkerRequestDTO) {
        List<Tuple> selectSubwayInfoAndCongestion = downCongestionRepository.selectSubwayInfoAndCongestion(subwayMarkerRequestDTO.getLatitude(),
                subwayMarkerRequestDTO.getLongitude(), subwayMarkerRequestDTO.getRadius(), subwayMarkerRequestDTO.getType());
        List<SubwayMarkerResponseDTO> results = selectSubwayInfoAndCongestion.stream()
                .map(o -> tupleConvertToSubwayMarkerResponseDTO(o, subwayMarkerRequestDTO.getTime())).collect(Collectors.toList());
        return results;
    }

    @Override
    public CarResponseDTO carCongestionInfo(CarRequestDTO carRequestDTO, int dir) {
        Duration unit = Duration.ofMinutes(10);
        LocalTime start = LocalTime.of(0, 0);
        ZoneOffset zoneOffSet = ZoneOffset.of("+09:00");
        OffsetDateTime currentTime = OffsetDateTime.now(zoneOffSet);
        long howMany = Duration.between(start, currentTime).dividedBy(unit);
        LocalTime requireTime = start.plus(howMany * 10, ChronoUnit.MINUTES);

        if(carRequestDTO.getTime() != null){
            requireTime = carRequestDTO.getTime();
        }

        List<CarCongestion> carCongestions = carCongestionRepository.selectCarCongestion(carRequestDTO.getRegion(), carRequestDTO.getLine(), carRequestDTO.getStation(), carRequestDTO.getType(), requireTime, dir);
        CarResponseDTO carResponseDTO = new CarResponseDTO();
        carResponseDTO.setLocalTime(requireTime);
        carResponseDTO.setCongestion(carCongestions.stream().map(o -> o.getCarCongestion()).collect(Collectors.toList()));
        return carResponseDTO;
    }

    public SubwayMarkerResponseDTO tupleConvertToSubwayMarkerResponseDTO(Tuple tuple, String currentTimeString) {

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        Duration unit = Duration.ofMinutes(30);
        LocalTime start = LocalTime.of(0, 0);
        LocalTime currentTime = LocalTime.parse(currentTimeString, timeFormatter);
        currentTime = currentTime.minus(30, ChronoUnit.MINUTES);

        long howMany = Duration.between(start, currentTime).dividedBy(unit);
        LocalTime requireTime = start.plus(howMany * 30, ChronoUnit.MINUTES);
        requireTime = requireTime.plus(30, ChronoUnit.MINUTES);

        Info curInfo = tuple.get(info);
        SubwayMarkerResponseDTO subwayMarkerResponseDTO = new SubwayMarkerResponseDTO();
        if (curInfo != null) {
            subwayMarkerResponseDTO.setRegion(curInfo.getRegion());
            subwayMarkerResponseDTO.setLine(curInfo.getLine());
            subwayMarkerResponseDTO.setStation(curInfo.getStation());
            subwayMarkerResponseDTO.setLatitude(curInfo.getInfoLatitude());
            subwayMarkerResponseDTO.setLongitude(curInfo.getInfoLongitude());
        }

        // left join에 의해서 null일 경우가 존재하기 때문에 조건문 안에 넣어줌
        // tuple은 일단 한 번 호출한 다음에 거기서 꺼내와야한다.
        if (tuple.get(upCongestion) != null) {
            UpCongestion up = tuple.get(upCongestion);
            subwayMarkerResponseDTO.setUpCongestion(localTimeToData(requireTime, up));
            subwayMarkerResponseDTO.setUpNextStation(up.getUpNextStation());
        }

        if (tuple.get(downCongestion) != null) {
            DownCongestion down = tuple.get(downCongestion);
            subwayMarkerResponseDTO.setDownCongestion(localTimeToData(requireTime, down));
            subwayMarkerResponseDTO.setDownNextStation(down.getDownNextStation());
        }

        return subwayMarkerResponseDTO;
    }
}
