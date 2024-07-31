package site.nonestep.idontwantwalk.member.service;

import site.nonestep.idontwantwalk.member.entity.SocialType;
import site.nonestep.idontwantwalk.member.entity.Member;
import site.nonestep.idontwantwalk.member.dto.*;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.member.dto.MemberSignUpRequestDTO;
import site.nonestep.idontwantwalk.member.dto.MemberSignUpResponseDTO;
import site.nonestep.idontwantwalk.member.repository.MemberRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberRepository memberRepository;

    //닉네임 배열을 위한
    String [] nick1 = new String[]{"성실한","재밌는","유쾌한","밝은","목마른","배아픈","청량한","심심한","차가운","지루한","불안한","행복한",
            "추위를 많이 타는","용감한","서글픈","웃고있는","감성적인","이성적인","말이 많은","무뚝뚝한","쾌활한","신나는","뒤집어진","걸어가는",
            "생각하는","배고픈","질서를 중요시하는","개구장이","귀여운","단호한","소심한","잠만보","감기에 걸린","맛있는 걸 좋아하는","특이한",
            "재치있는","배고픔을 못참는","독특한","과자러버","디저트를 사랑하는","빵순이","빵돌이","한식이 좋은","양식이 좋은","나는","준비된",
            "여름이 좋은","겨울이 좋은","졸린","동그란","예민한","마음이 고운","사람이 좋은","게임중독","긴장한","더위를 많이타는","운동하는",
            "긴장한","울랄라","즐거울까말까","해피니스","혼자가 아닌","우울하지 않은","질투쟁이","새침한","까탈레나","까다로운","오늘은","내일은",
            "일주일 뒤에","물을 좋아하는","수영하는","배영하는","접영하는","평형하는","태권도를 사랑하는","한국의","여행가고싶은","커피를 좋아하는",
            "사랑의","행복의","빠른","여유로운","조용한","시끄러운","공연예정인","틱토커","인플루언서","요즘 핫플"
    };
    String [] nick2 = new String[]{"일호선","이호선","삼호선","사호선","오호선","육호선","칠호선","팔호선","구호선","공항철도","부산김해경전철",
            "동해선","인천2호선","대구3호선","광주1호선","수인분당선","신분당선","경의중앙선","경춘선","우이신설선","신림선","김포골드라인","에버라인",
            "서해선","의정부경전철","인천1호선","대구1호선","대구2호선","부산1호선","부산2호선","부산3호선","부산4호선","대전1호선","지하철",
            "전철","서울지하철","철도교통공사","메트로","서울교통공사","코레일","부산교통공사","대구도시철도공사","인천교통공사","광주광역시도시철도공사",
            "대전도시철도공사","신분당선주식회사","용인경전철주식회사","의정부경전철주식회사","김포골드라인운영주식회사"
    };

    //회원가입
    @Override
    public MemberSignUpResponseDTO signUp(MemberSignUpRequestDTO memberSignUpRequestDTO) {

        //랜덤숫자태그
        int randomTag = (int)(Math.random() * (99999-10000+1))+10000;

        Member signUp = memberRepository.save(
                Member.builder()
                        .memberID(memberSignUpRequestDTO.getMemberID())
                        .memberPassword(memberSignUpRequestDTO.getMemberPass())
                        .memberName(memberSignUpRequestDTO.getMemberName())
                        .memberMail(memberSignUpRequestDTO.getMemberMail())
                        .memberPhone(memberSignUpRequestDTO.getMemberPhone())
                        .memberSocialType(SocialType.NOMAL)
                        .memberJoinTime(LocalDateTime.now())
                        .memberNickname(nick1[(int)(Math.random()*nick1.length)]+nick2[(int)(Math.random()* nick2.length)])
                        .memberRandom("#"+randomTag)
                        .build()
        );

        MemberSignUpResponseDTO memberSignUpResponseDTO = new MemberSignUpResponseDTO();
        memberSignUpResponseDTO.setMemberID(signUp.getMemberID());
        memberSignUpResponseDTO.setMemberName(signUp.getMemberName());
        memberSignUpResponseDTO.setMemberNickname(signUp.getMemberNickname());
        memberSignUpResponseDTO.setMemberRandom(signUp.getMemberRandom());
        memberSignUpResponseDTO.setMemberJoinDate(signUp.getMemberJoinTime());

        return memberSignUpResponseDTO;
    }
    //회원가입 시 id 중복체크
    @Override
    public Boolean memberIdCheck(MemberIdCheckRequestDTO memberIdCheckRequestDTO) {
        Optional<String> memberIdCheck = memberRepository.selectMemberID(memberIdCheckRequestDTO.getMemberID());
        if (memberIdCheck.isEmpty()){
            return true;
        }else{
            return false;
        }
    }


    //id 찾기
    @Override
    public List<MemberIdFindResponseDTO> idFind(MemberIdFindRequestDTO memberIdFindRequestDTO) {
        List<Member> memberIdFind = memberRepository.selectMemberNameAndMemberPhone(memberIdFindRequestDTO.getMemberName(),
                memberIdFindRequestDTO.getMemberPhone());

        List<MemberIdFindResponseDTO> memberIdFindResponse = memberIdFind.stream().map(this::listIdFind).toList();
        return memberIdFindResponse;
    }


    //id 찾기 <<list로 받아올 때는 하나 더 작성해야한다.
    public MemberIdFindResponseDTO listIdFind (Member member){
        MemberIdFindResponseDTO memberIdFindResponseDTO = new MemberIdFindResponseDTO();

        memberIdFindResponseDTO.setMemberID(member.getMemberID());
        memberIdFindResponseDTO.setMemberJoinDate(member.getMemberJoinTime());
        return memberIdFindResponseDTO;
    }

    //일반 로그인
    @Override
    public Long login(String memberID, String memberPass) {
        Long selectMemberIdAndMemberPass = memberRepository.selectMemberIdAndMemberPass(memberID, memberPass);
        return selectMemberIdAndMemberPass;
    }

    // DB에 refreshtoken  저장
    @Override
    public void refreshlogin(Long memberNo, String memberRefreshToken) {
        Member member = memberRepository.getReferenceById(memberNo);
        member.changeToken(memberRefreshToken);  //void라서 return안함
    }
}