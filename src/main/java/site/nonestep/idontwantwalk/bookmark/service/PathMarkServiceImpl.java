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

        if (selectPathMarkCount > 4){
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
