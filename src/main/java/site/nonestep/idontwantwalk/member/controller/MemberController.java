package site.nonestep.idontwantwalk.member.controller;

import com.mysql.cj.x.protobuf.Mysqlx;
import jakarta.validation.Valid;//유효성검사해라, 따라서 회원가입에서만 사용한다.
import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.response.SingleMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.config.AuthConfig;
import site.nonestep.idontwantwalk.member.dto.*;
import site.nonestep.idontwantwalk.member.service.MemberService;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@RestController
@CrossOrigin("*")//""안에있는 것들을 허용해주겠다.
@RequestMapping("/member")//시작하는 url뒤에 공통으로 붙는 키워드
public class MemberController {
    @Autowired
    private MemberService memberService;

    // 휴대폰 인증번호 발신
    public static DefaultMessageService messageService;

    // 관리자가 휴대폰 인증을 보내기 위한 것
    @Autowired
    private AuthConfig authConfig;

    // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // 여기서부터 휴대폰 인증 Controller
    @Autowired
    public void setDefaultMessageService(AuthConfig authConfig) {
        // 반드시 계정 내 등록된 유효한 API 키, API Secret Key를 입력해주셔야 합니다!
        messageService = NurigoApp.INSTANCE.initialize(authConfig.getCoolsmsapikey(), authConfig.getCoolsmssecretkey(), "https://api.coolsms.co.kr");
    }

    // 회원가입 - 휴대폰 인증 시 6자리 랜덤 숫자 생성
    public static int generateAuthNo1() {
        return ThreadLocalRandom.current().nextInt(100000, 1000000);
    }

    //회원가입 api
    @PostMapping("/signup")//?는 다 받아주겟다
    public ResponseEntity<?> signup(@Valid @RequestBody MemberSignUpRequestDTO memberSignUpRequestDTO){

        MemberSignUpResponseDTO memberSignUpResponseDTO = memberService.signUp(memberSignUpRequestDTO);

        return new ResponseEntity<MemberSignUpResponseDTO>(memberSignUpResponseDTO, HttpStatus.OK);
    }

    // 휴대폰 인증
    @PostMapping("/phone")
    public ResponseEntity<?> phone(@RequestBody MemberPhoneRequestDTO memberPhoneRequestDTO) {

        Message message = new Message();
        int randomPhone = generateAuthNo1();//46번째 줄 만들어둔 변수를 가져온 것임

        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 합니다.
        message.setFrom(authConfig.getPhone());
        message.setTo(memberPhoneRequestDTO.getMemberPhone());//memberphone에 있는 memberphone에 보낼거야
        message.setText("[이호선] 본인확인 인증번호는 [" + randomPhone + "] 입니다. 인증번호를 입력해주세요.");

        SingleMessageSentResponse response = this.messageService.sendOne(new SingleMessageSendingRequest(message));
        MemberPhoneResponseDTO memberPhoneResponseDTO = new MemberPhoneResponseDTO();
        memberPhoneResponseDTO.setAuthenticationNumber(String.valueOf(randomPhone));

        return new ResponseEntity<MemberPhoneResponseDTO>(memberPhoneResponseDTO, HttpStatus.OK);
    }

    //회원가입 시 아이디 중복 검사
    @GetMapping("/idcheck")
    public ResponseEntity<?> idCheck(@ModelAttribute MemberIdCheckRequestDTO memberIdCheckRequestDTO){
        Boolean isIdCheck = memberService.memberIdCheck(memberIdCheckRequestDTO);
        return new ResponseEntity<>(isIdCheck, HttpStatus.OK);
    }

}
