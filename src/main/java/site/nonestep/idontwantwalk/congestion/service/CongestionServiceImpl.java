package site.nonestep.idontwantwalk.congestion.service;

import com.querydsl.core.Tuple;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.congestion.dto.*;
import site.nonestep.idontwantwalk.congestion.entity.DownCongestion;
import site.nonestep.idontwantwalk.congestion.entity.QUpCongestion;
import site.nonestep.idontwantwalk.congestion.entity.UpCongestion;
import site.nonestep.idontwantwalk.congestion.entity.UpEtc;
import site.nonestep.idontwantwalk.congestion.repository.DownCongestionRepository;
import site.nonestep.idontwantwalk.congestion.repository.DownEtcRepository;
import site.nonestep.idontwantwalk.congestion.repository.UpCongestionRepository;
import site.nonestep.idontwantwalk.congestion.repository.UpEtcRepository;
import site.nonestep.idontwantwalk.subway.entity.Info;

import static site.nonestep.idontwantwalk.congestion.entity.QDownCongestion.*;
import static site.nonestep.idontwantwalk.subway.entity.QInfo.*;
import static site.nonestep.idontwantwalk.congestion.entity.QUpCongestion.upCongestion;

import java.math.BigDecimal;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    // 해당 역의 상행선 혼잡도 흐름, 현재 시간부터 30분 뒤, 60분 뒤
    @Override
    public UpTimeResponseDTO upTime(UpTimeRequestDTO upTimeRequestDTO) {
        UpCongestion up = upCongestionRepository.selectCurrentTimeTo1Hours(upTimeRequestDTO.getRegion(), upTimeRequestDTO.getLine(),
                upTimeRequestDTO.getStation(), upTimeRequestDTO.getType());

        if (up == null) {
            return null;
        } else {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

            Duration unit = Duration.ofMinutes(30);

            LocalTime start = LocalTime.of(0, 0);
            LocalTime currentTime = LocalTime.parse(upTimeRequestDTO.getTime(), timeFormatter);
            currentTime = currentTime.minus(1,ChronoUnit.MINUTES);

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
    @Override
    public DownTimeResponseDTO downTime(DownTimeRequestDTO downTimeRequestDTO) {
        DownCongestion down = downCongestionRepository.selectCurrentTimeTo1Hours(downTimeRequestDTO.getRegion(),
                downTimeRequestDTO.getLine(), downTimeRequestDTO.getStation(), downTimeRequestDTO.getType());

        if (down == null) {
            return null;
        } else {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");

            Duration unit = Duration.ofMinutes(30);

            LocalTime start = LocalTime.of(0, 0);
            LocalTime currentTime = LocalTime.parse(downTimeRequestDTO.getTime(), timeFormatter);
            currentTime = currentTime.minus(1,ChronoUnit.MINUTES);

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

    public String congestionToState(BigDecimal num) {
        int spare = num.compareTo(BigDecimal.valueOf(20));
        int normal = num.compareTo(BigDecimal.valueOf(40));
        int caution = num.compareTo(BigDecimal.valueOf(60));

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
                subwayMarkerRequestDTO.getLongitude(),subwayMarkerRequestDTO.getRadius(),subwayMarkerRequestDTO.getType());
        List<SubwayMarkerResponseDTO> results = selectSubwayInfoAndCongestion.stream()
                .map( o -> tupleConvertToSubwayMarkerResponseDTO(o,subwayMarkerRequestDTO.getTime())).collect(Collectors.toList());
        return results;
    }
    public SubwayMarkerResponseDTO tupleConvertToSubwayMarkerResponseDTO(Tuple tuple,String currentTime){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmm");
        LocalTime localTime = LocalTime.parse(currentTime, formatter);
        Info curInfo = tuple.get(info);
        SubwayMarkerResponseDTO subwayMarkerResponseDTO = new SubwayMarkerResponseDTO();
        if(curInfo != null) {
            subwayMarkerResponseDTO.setRegion(curInfo.getRegion());
            subwayMarkerResponseDTO.setLine(curInfo.getLine());
            subwayMarkerResponseDTO.setStation(curInfo.getStation());
        }
        subwayMarkerResponseDTO.setUpCongestion(localTimeToData(localTime,tuple.get(upCongestion)));
        subwayMarkerResponseDTO.setDownCongestion(localTimeToData(localTime,tuple.get(downCongestion)));
        subwayMarkerResponseDTO.setUpNextStation(tuple.get(upCongestion.upNextStation));
        subwayMarkerResponseDTO.setDownNextStation(tuple.get(downCongestion.downNextStation));

        return subwayMarkerResponseDTO;
    }

}
