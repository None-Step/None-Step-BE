package site.nonestep.idontwantwalk.bookmark.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.bookmark.dto.*;
import site.nonestep.idontwantwalk.bookmark.entity.PathMark;
import site.nonestep.idontwantwalk.bookmark.repository.PathMarkRepository;
import site.nonestep.idontwantwalk.member.entity.Member;
import site.nonestep.idontwantwalk.member.repository.MemberRepository;

import java.util.List;

@Slf4j
@Service
@Transactional
public class PathMarkServiceImpl implements PathMarkService{

    @Autowired
    private PathMarkRepository pathMarkRepository;

    @Autowired
    private MemberRepository memberRepository;

    // [경로] 즐겨 찾기 등록
    @Override
    public PathRegisterResponseDTO pathRegister(PathRegisterRequestDTO pathRegisterRequestDTO, Long memberNo) {
        Member member = memberRepository.getReferenceById(memberNo);

        Long selectPathMarkCount = pathMarkRepository.selectPathMarkCount(memberNo);
        Long selectSamePath = pathMarkRepository.selectSamePath(pathRegisterRequestDTO.getStartLatitude(), pathRegisterRequestDTO.getStartLongitude(), pathRegisterRequestDTO.getEndLatitude(),
                pathRegisterRequestDTO.getEndLongitude(), memberNo);

        // selectSamePath가 0이 아니라면 이미 등록을 했다는 것이므로 null 반환처리
        // try catch로 처리하지 않은 이유 : 모든 오류 값을 catch로 보내기 때문에 어떤 오류 발생인지 정확히 알 수 없으므로 이렇게 처리함
        if (selectPathMarkCount > 4 || selectSamePath != 0){
            return null;
        }else{
            pathMarkRepository.save(
                    PathMark.builder()
                            .pathStartNickName(pathRegisterRequestDTO.getPathStartNickName())
                            .pathEndNickName(pathRegisterRequestDTO.getPathEndNickName())
                            .pathStartLatitude(pathRegisterRequestDTO.getStartLatitude())
                            .pathStartLongitude(pathRegisterRequestDTO.getStartLongitude())
                            .pathEndLatitude(pathRegisterRequestDTO.getEndLatitude())
                            .pathEndLongitude(pathRegisterRequestDTO.getEndLongitude())
                            .pathColor(pathRegisterRequestDTO.getPathColor())
                            .memberNo(member)
                            .build()
            );
            PathRegisterResponseDTO pathRegisterResponseDTO = new PathRegisterResponseDTO();
            pathRegisterResponseDTO.setMessage("Success");
            return pathRegisterResponseDTO;
        }
    }

    // [경로] 즐겨 찾기 조회
    @Override
    public List<PathListResponseDTO> pathList(Long memberNo) {
        List<PathListResponseDTO> selectPathList = pathMarkRepository.selectPathList(memberNo);
        return selectPathList;
    }


    // [경로] 즐겨 찾기 삭제
    @Override
    public PathDeleteResponseDTO pathDelete(PathDeleteRequestDTO pathDeleteRequestDTO, Long memberNo) {

        PathMark pathMark = pathMarkRepository.getReferenceById(pathDeleteRequestDTO.getPathNo());

        if (pathMark.getMemberNo().getMemberNo() != memberNo){
            return null;
        }else{
            pathMarkRepository.deletePath(pathDeleteRequestDTO.getPathNo());
            PathDeleteResponseDTO pathDeleteResponseDTO = new PathDeleteResponseDTO();
            pathDeleteResponseDTO.setMessage("Success");
            return pathDeleteResponseDTO;
        }
    }
}
