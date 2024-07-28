package site.nonestep.idontwantwalk.subway.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.subway.repository.*;

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
}
