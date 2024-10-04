package site.nonestep.idontwantwalk.bookmark.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.bookmark.dto.PlaceRegisterRequestDTO;
import site.nonestep.idontwantwalk.bookmark.dto.PlaceRegisterResponseDTO;
import site.nonestep.idontwantwalk.bookmark.entity.PlaceMark;
import site.nonestep.idontwantwalk.bookmark.repository.PlaceMarkRepository;
import site.nonestep.idontwantwalk.member.entity.Member;
import site.nonestep.idontwantwalk.member.repository.MemberRepository;

@Slf4j
@Service
@Transactional
public class PlaceMarkServiceImpl implements PlaceMarkService {

    @Autowired
    private PlaceMarkRepository placeMarkRepository;

    @Autowired
    private MemberRepository memberRepository;

    // [장소] 즐겨 찾기 등록
    @Override
    public PlaceRegisterResponseDTO placeRegister(PlaceRegisterRequestDTO placeRegisterRequestDTO, Long memberNo) {

        Long selectPlaceMarkCount = placeMarkRepository.selectPlaceMarkCount(memberNo);
        Member member = memberRepository.getReferenceById(memberNo);

        if (selectPlaceMarkCount > 4){
            return null;
        }else{
            placeMarkRepository.save(
                    PlaceMark.builder()
                            .placeNickName(placeRegisterRequestDTO.getPlaceNickName())
                            .placeLatitude(placeRegisterRequestDTO.getLatitude())
                            .placeLongitude(placeRegisterRequestDTO.getLongitude())
                            .placeAddress(placeRegisterRequestDTO.getPlaceAddress())
                            .placeColor(placeRegisterRequestDTO.getPlaceColor())
                            .memberNo(member)
                            .build()
            );

            PlaceRegisterResponseDTO placeRegisterResponseDTO = new PlaceRegisterResponseDTO();
            placeRegisterResponseDTO.setMessage("Success");

            return placeRegisterResponseDTO;
        }
    }
}
