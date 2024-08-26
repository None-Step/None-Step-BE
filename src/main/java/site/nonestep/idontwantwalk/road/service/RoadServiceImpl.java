package site.nonestep.idontwantwalk.road.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.road.dto.SubwayPathRequestDTO;
import site.nonestep.idontwantwalk.road.dto.SubwayPathResponseDTOX;
import site.nonestep.idontwantwalk.subway.repository.SubwayInfoRepository;

import java.util.Optional;

@Slf4j
@Service
@Transactional
public class RoadServiceImpl implements RoadService{

    @Autowired
    private SubwayInfoRepository subwayInfoRepository;


    @Override
    public SubwayPathResponseDTOX selectCidAndSid(String region, String line, String station) {
        Optional<SubwayPathResponseDTOX> subwayCidAndSid = subwayInfoRepository.selectSidAndCid(region, line, station);

        if (subwayCidAndSid.isEmpty()){
            return null;
        }else{
            return subwayCidAndSid.get();
        }
    }
}
