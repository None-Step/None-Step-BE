package site.nonestep.idontwantwalk.subway.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.subway.dto.*;
import site.nonestep.idontwantwalk.subway.entity.Info;
import site.nonestep.idontwantwalk.subway.repository.*;

import java.math.BigDecimal;
import java.util.List;

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

}
