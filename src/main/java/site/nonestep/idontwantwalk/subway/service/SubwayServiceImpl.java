package site.nonestep.idontwantwalk.subway.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.subway.dto.*;
import site.nonestep.idontwantwalk.subway.entity.Info;
import site.nonestep.idontwantwalk.subway.repository.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class SubwayServiceImpl implements SubwayService{

    @Autowired
    private SubwayInfoRepository subwayInfoRepository;

    @Autowired
    private SubwayEscalRepository subwayEscalRepository;

    @Autowired
    private SubwayElevatorRepository subwayElevatorRepository;

    @Autowired
    private SubwayToiletRepository subwayToiletRepository;

    @Autowired
    private SubwayDifToiletRepository subwayDifToiletRepository;

    @Autowired
    private SubwayATMRepository subwayATMRepository;

    @Autowired
    private SubwayCenterRepository subwayCenterRepository;

    @Autowired
    private SubwayNursingRepository subwayNursingRepository;

    @Autowired
    private SubwayAedRepository subwayAedRepository;

    @Autowired SubwayLiftRepository subwayLiftRepository;

    @Autowired
    private SubwayChargerRepository subwayChargerRepository;

    // 위치기반 역 조회 및 검색
    // 원래 Repository에서 SQL 처리 후, ServiceImpl에서 변환해서 Controller로 보내야 했는데
    // RepositoryImpl에서 DB에 없는 distance를 보내다 보니 RepositoryImpl에서 모두 처리 후
    // ServiceImpl에서는 그냥 바로 전송만 하면 됨
    @Override
    public List<SubwayLocationResponseDTO> location(BigDecimal latitude, BigDecimal longitude, Long radius) {
        List<SubwayLocationResponseDTO> subwayLocationResponseDTO = subwayInfoRepository.selectInfoLatitudeAndInfoLongitudeAndRadius(latitude,
                longitude, radius).stream().filter(a -> a.getDistance() <= radius).toList();

        return subwayLocationResponseDTO;
    }

    // 엘리베이터 있는 역 조회
    @Override
    public List<SubwayElevatorResponseDTO> elevator(BigDecimal latitude, BigDecimal longitude, Long radius) {
        List<SubwayElevatorResponseDTO> subwayElevatorResponseDTO = subwayElevatorRepository.selectElevator(latitude,
                longitude, radius).stream().filter(a -> a.getDistance() <= radius).toList();

        return subwayElevatorResponseDTO;
    }

    // 에스컬레이터 있는 역 조회
    @Override
    public List<SubwayEscalResponseDTO> escal(BigDecimal latitude, BigDecimal longitude, Long radius) {
        List<SubwayEscalResponseDTO> subwayEscalResponseDTO = subwayEscalRepository.selectEscal(latitude,
                longitude, radius).stream().filter(a -> a.getDistance() <= radius).toList();

        return subwayEscalResponseDTO;
    }

    // 휠체어 리프트 있는 역 조회
    @Override
    public List<SubwayLiftResponseDTO> lift(BigDecimal latitude, BigDecimal longitude, Long radius) {
        List<SubwayLiftResponseDTO> subwayLiftResponseDTO = subwayLiftRepository.selectLift(latitude,
                longitude, radius).stream().filter(a -> a.getDistance() <= radius).toList();

        return subwayLiftResponseDTO;
    }

    // 역 내 화장실 조회
    @Override
    public List<SubwayToiletResponseDTO> toilet(BigDecimal latitude, BigDecimal longitude, Long radius) {
        List<SubwayToiletResponseDTO> subwayToiletResponseDTO = subwayToiletRepository.selectToilet(latitude,
                longitude, radius).stream().filter(a -> a.getDistance() <= radius).toList();

        return subwayToiletResponseDTO;
    }

    // 역 내 장애인 화장실 조회
    @Override
    public List<SubwayDifToiletResponseDTO> difToilet(BigDecimal latitude, BigDecimal longitude, Long radius) {
        List<SubwayDifToiletResponseDTO> subwayDifToiletResponseDTO = subwayDifToiletRepository.selectDifToilet(latitude,
                longitude, radius).stream().filter(a -> a.getDistance() <= radius).toList();

        return subwayDifToiletResponseDTO;
    }

    // 역 내 수유실 조회
    @Override
    public List<SubwayNursingResponseDTO> nursingRoom(BigDecimal latitude, BigDecimal longitude, Long radius) {
        List<SubwayNursingResponseDTO> subwayNursingResponseDTO = subwayNursingRepository.selectNursingRoom(latitude,
                longitude, radius).stream().filter(a -> a.getDistance() <= radius).toList();

        return subwayNursingResponseDTO;
    }

    // 역 내 atm 조회
    @Override
    public List<SubwayATMResponseDTO> atm(BigDecimal latitude, BigDecimal longitude, Long radius) {
        List<SubwayATMResponseDTO> subwayATMResponseDTO = subwayATMRepository.selectATM(latitude,
                longitude, radius).stream().filter(a -> a.getDistance() <= radius).toList();

        return subwayATMResponseDTO;
    }

    // 역 내 AED(제세동기) 조회
    @Override
    public List<SubwayAEDResponseDTO> aed(BigDecimal latitude, BigDecimal longitude, Long radius) {
        List<SubwayAEDResponseDTO> subwayAEDResponseDTO = subwayAedRepository.selectAED(latitude,
                longitude, radius).stream().filter(a -> a.getDistance() <= radius).toList();

        return subwayAEDResponseDTO;
    }

    // 역 내 전동 휠체어 충전 설비 조회
    @Override
    public List<SubwayChargerResponseDTO> charger(BigDecimal latitude, BigDecimal longitude, Long radius) {
        List<SubwayChargerResponseDTO> subwayChargerResponseDTO = subwayChargerRepository.selectCharger(latitude,
                longitude, radius).stream().filter(a -> a.getDistance() <= radius).toList();

        return subwayChargerResponseDTO;
    }

    // 역 내 고객센터 조회
    @Override
    public List<SubwayCenterResponseDTO> center(BigDecimal latitude, BigDecimal longitue, Long radius) {
        List<SubwayCenterResponseDTO> subwayCenterResponseDTO = subwayCenterRepository.selectCenter(latitude,
                longitue, radius).stream().filter(a -> a.getDistance() <= radius).toList();

        return subwayCenterResponseDTO;
    }

    // 역 1개 정보 전체 조회
    @Override
    public SubwayStationInfoResponseDTO totalStationInfo(SubwayStationInfoRequestDTO subwayStationInfoRequestDTO) {
        Optional<Info> infoStation = subwayInfoRepository.selectInfo(subwayStationInfoRequestDTO.getRegion(),
                subwayStationInfoRequestDTO.getLine(), subwayStationInfoRequestDTO.getStation());
        List<InfoEscal> escalInfo = subwayEscalRepository.selectEscal(subwayStationInfoRequestDTO.getRegion(),
                subwayStationInfoRequestDTO.getLine(), subwayStationInfoRequestDTO.getStation());
        List<InfoElevator> elevatorInfo = subwayElevatorRepository.selectInfoElevator(subwayStationInfoRequestDTO.getRegion(),
                subwayStationInfoRequestDTO.getLine(), subwayStationInfoRequestDTO.getStation());
        List<InfoToilet> toiletInfo = subwayToiletRepository.selectInfoToilet(subwayStationInfoRequestDTO.getRegion(),
                subwayStationInfoRequestDTO.getLine(), subwayStationInfoRequestDTO.getStation());
        List<InfoDifToilet> difToiletInfo = subwayDifToiletRepository.selectInfoDifToilet(subwayStationInfoRequestDTO.getRegion(),
                subwayStationInfoRequestDTO.getLine(), subwayStationInfoRequestDTO.getStation());
        List<InfoLift> liftInfo = subwayLiftRepository.selectInfoLift(subwayStationInfoRequestDTO.getRegion(),
                subwayStationInfoRequestDTO.getLine(), subwayStationInfoRequestDTO.getStation());
        List<InfoCharger> chargerInfo = subwayChargerRepository.selectInfoCharger(subwayStationInfoRequestDTO.getRegion(),
                subwayStationInfoRequestDTO.getLine(), subwayStationInfoRequestDTO.getStation());
        List<InfoNursing> nursingInfo = subwayNursingRepository.selectInfoNursing(subwayStationInfoRequestDTO.getRegion(),
                subwayStationInfoRequestDTO.getLine(), subwayStationInfoRequestDTO.getStation());
        List<InfoAtm> atmInfo = subwayATMRepository.selectInfoAtm(subwayStationInfoRequestDTO.getRegion(),
                subwayStationInfoRequestDTO.getLine(), subwayStationInfoRequestDTO.getStation());
        List<InfoAed> aedInfo = subwayAedRepository.selectAed(subwayStationInfoRequestDTO.getRegion(),
                subwayStationInfoRequestDTO.getLine(), subwayStationInfoRequestDTO.getStation());
        List<InfoCenter> centerInfo = subwayCenterRepository.selectInfoCenter(subwayStationInfoRequestDTO.getRegion(),
                subwayStationInfoRequestDTO.getLine(), subwayStationInfoRequestDTO.getStation());

        SubwayStationInfoResponseDTO subwayStationInfoResponseDTO = new SubwayStationInfoResponseDTO();

        if (infoStation.isEmpty()){
            return null;
        }else{
            subwayStationInfoResponseDTO.setInfoRegion(subwayStationInfoRequestDTO.getRegion());
            subwayStationInfoResponseDTO.setInfoLine(subwayStationInfoRequestDTO.getLine());
            subwayStationInfoResponseDTO.setInfoStation(subwayStationInfoRequestDTO.getStation());
            subwayStationInfoResponseDTO.setInfoAddress(infoStation.get().getInfoAddress());
            subwayStationInfoResponseDTO.setInfoLatitude(infoStation.get().getInfoLatitude());
            subwayStationInfoResponseDTO.setInfoLongitude(infoStation.get().getInfoLongitude());
            subwayStationInfoResponseDTO.setInfoTransfer(infoStation.get().getInfoTransfer());
            subwayStationInfoResponseDTO.setInfoWeekDayStart(infoStation.get().getInfoWeekdayStart());
            subwayStationInfoResponseDTO.setInfoWeekDayEnd(infoStation.get().getInfoWeekdayEnd());
            subwayStationInfoResponseDTO.setInfoSatStart(infoStation.get().getInfoSatStart());
            subwayStationInfoResponseDTO.setInfoSatEnd(infoStation.get().getInfoSatEnd());
            subwayStationInfoResponseDTO.setInfoHolidayStart(infoStation.get().getInfoHolidayStart());
            subwayStationInfoResponseDTO.setInfoHolidayEnd(infoStation.get().getInfoHolidayEnd());
            subwayStationInfoResponseDTO.setEscal(escalInfo);
            subwayStationInfoResponseDTO.setElevator(elevatorInfo);
            subwayStationInfoResponseDTO.setToilet(toiletInfo);
            subwayStationInfoResponseDTO.setDifToilet(difToiletInfo);
            subwayStationInfoResponseDTO.setLift(liftInfo);
            subwayStationInfoResponseDTO.setCharger(chargerInfo);
            subwayStationInfoResponseDTO.setNursingRoom(nursingInfo);
            subwayStationInfoResponseDTO.setAtm(atmInfo);
            subwayStationInfoResponseDTO.setAed(aedInfo);
            subwayStationInfoResponseDTO.setCenter(centerInfo);

            return subwayStationInfoResponseDTO;
        }
    }


}
