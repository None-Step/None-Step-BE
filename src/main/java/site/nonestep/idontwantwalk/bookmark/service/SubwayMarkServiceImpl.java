package site.nonestep.idontwantwalk.bookmark.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.bookmark.dto.*;
import site.nonestep.idontwantwalk.bookmark.entity.MemberSubWayBookMark;
import site.nonestep.idontwantwalk.bookmark.entity.SubwayMark;
import site.nonestep.idontwantwalk.bookmark.repository.SubwayMarkRepository;
import site.nonestep.idontwantwalk.member.entity.Member;
import site.nonestep.idontwantwalk.member.repository.MemberRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
public class SubwayMarkServiceImpl implements SubwayMarkService {

    @Autowired
    private SubwayMarkRepository subwayMarkRepository;

    @Autowired
    private MemberRepository memberRepository;

    // [지하철 역] 즐겨 찾기 등록
    @Override
    public SubwayRegisterResponseDTO subwayRegister(SubwayRegisterRequestDTO subwayRegisterRequestDTO, Long memberNo) {
        Member member = memberRepository.getReferenceById(memberNo);
        Long selectSubwayMarkCount = subwayMarkRepository.selectSubwayMarkCount(memberNo);
        Long selectSameSubway = subwayMarkRepository.selectSameSubway(subwayRegisterRequestDTO.getRegion(), subwayRegisterRequestDTO.getLine(),
                subwayRegisterRequestDTO.getStation());

        if (selectSubwayMarkCount > 4 || selectSameSubway != 0) {
            return null;
        } else {
            subwayMarkRepository.save(
                    SubwayMark.builder()
                            .region(subwayRegisterRequestDTO.getRegion())
                            .line(subwayRegisterRequestDTO.getLine())
                            .station(subwayRegisterRequestDTO.getStation())
                            .memberNo(memberNo)
                            .build()
            );

            SubwayRegisterResponseDTO subwayRegisterResponseDTO = new SubwayRegisterResponseDTO();
            subwayRegisterResponseDTO.setMessage("Success");

            return subwayRegisterResponseDTO;
        }
    }

    // [지하철 역] 즐겨 찾기 조회
    @Override
    public List<SubwayListResponseDTO> subwayList(Long memberNo) {

        List<SubwayListResponseDTO> selectSubwayMark = subwayMarkRepository.selectSubwayMark(memberNo);

        return selectSubwayMark;
    }

    // [지하철 역] 즐겨 찾기 삭제
    @Override
    public SubwayDeleteResponseDTO subwayDelete(SubwayDeleteRequestDTO subwayDeleteRequestDTO, Long memberNo) {
        // 해당 Table은 PK가 총 4개 이므로 아래와 같이 코드를 짠다.
        // 근데.. 굳이 if문으로 회원이 일치하는지 확인할 필요 없이 모든 컬럼을 검사하므로 여기서는 바로 Delete처리한다.
        // 그래서 아래 코드는 굳이 필요 X

//        MemberSubWayBookMark subWayBookMark = new MemberSubWayBookMark();
//        subWayBookMark.setRegion(subwayDeleteRequestDTO.getRegion());
//        subWayBookMark.setLine(subwayDeleteRequestDTO.getLine());
//        subWayBookMark.setStation(subwayDeleteRequestDTO.getStation());
//        subWayBookMark.setMemberNo(memberNo);
//
//        SubwayMark subwayMark = subwayMarkRepository.getReferenceById(subWayBookMark);

        subwayMarkRepository.deleteSubwayMark(subwayDeleteRequestDTO.getRegion(),
                subwayDeleteRequestDTO.getLine(), subwayDeleteRequestDTO.getStation(), memberNo);

        SubwayDeleteResponseDTO subwayDeleteResponseDTO = new SubwayDeleteResponseDTO();
        subwayDeleteResponseDTO.setMessage("Success");

        return subwayDeleteResponseDTO;
    }
}
