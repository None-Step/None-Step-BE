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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.auth.jwt.JsonWebToken;
import site.nonestep.idontwantwalk.auth.util.JwtTokenUtils;
import site.nonestep.idontwantwalk.config.AuthConfig;
import site.nonestep.idontwantwalk.member.dto.*;
import site.nonestep.idontwantwalk.member.service.MemberService;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static site.nonestep.idontwantwalk.auth.util.JwtTokenUtils.REFRESH_PERIOD;

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

    //ID 찾기
    @PostMapping("/idfind") //response에서 받아서! request로 전해준다.
    public ResponseEntity<?> idfind(@RequestBody MemberIdFindRequestDTO memberIdFindRequestDTO){
        List<MemberIdFindResponseDTO> memberIdFindResponseDTO = memberService.idFind(memberIdFindRequestDTO);

        //id 있을 수도 있고, 없을수도 있지 정보가 있으면 보내고, 정보가 없으면 안보내겠다.
        if (memberIdFindResponseDTO.isEmpty()) {
            return new ResponseEntity<>("회원정보가 없습니다.",HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(memberIdFindResponseDTO, HttpStatus.OK);
        }
    }

//    //일반로그인
//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody){
//
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    // 일반 로그인
    @PostMapping("/login")
    public ResponseEntity<?> normalLogin(@RequestBody MemberLoginRequestDTO memberLoginRequestDTO){

        Long login = memberService.login(memberLoginRequestDTO.getMemberID(), memberLoginRequestDTO.getMemberPass());

        if (login != null){
            // ROLE_USER : user인지 admin인지 같이 판별하기 위해서 보내는 것
            JsonWebToken jsonWebToken = JwtTokenUtils.allocateToken(login, "ROLE_USER");
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add("Authorization", jsonWebToken.getAccessToken());

            // path("/") : Cookie는 FE에서 설정 없이 접근하기 때문에 해당 쿠키가 어떤 url에서 사용하고 안하고를 정할 수 있음.
            // header같은 경우, 호출이나 값을 빼오는 코드를 모두 적어줘야한다.
            // Cookie는 코드 구현이 필요2 없다. Chrome 등 exp에서 가지고 있다가 자동으로 보내준다(크롬이)
            // 우리는 그래서 아래 코드와 같이 추가 설정만 해주는 것이다.
            // 그러나 "/"만 적게 되면 어떤 url에서도 Cookie를 보내겠다는 뜻
            // "/abcd" 등 /뒤에 path를 적게 되면 해당 path로만 Cookie를 보내게 됨
            // sameSite("None") : 다른 사이트에서도 접근 가능함 > LocalHost에서 nonestep.site로 접근 가능
            // sameSite("None")을 안써야하는거 아냐? 우리는 지금 개발단계이니까 풀어두고, 실제 배포할 때에는 지워야함!
            // 그러나 우리는 배우는 단계니까.. 걍 둔다.. ㅎㅎ
            // secure(true) : http뿐만 아니라 https에서도 보내겠다. 둘 다 허용한다!
            memberService.refreshlogin(login, jsonWebToken.getRefreshToken());
            ResponseCookie cookie = ResponseCookie.from("Refresh", jsonWebToken.getRefreshToken())
                    .sameSite("None")
                    .secure(true)
                    .path("/")
                    .maxAge(REFRESH_PERIOD)
                    .build();
            headers.add("Set-Cookie", cookie.toString());

            MemberLoginResponseDTO memberLoginResponseDTO = new MemberLoginResponseDTO();
            memberLoginResponseDTO.setMessage("Success");

            return new ResponseEntity<>(memberLoginResponseDTO, headers, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("ID와 비밀번호를 확인해주세요", HttpStatus.BAD_REQUEST);
        }
    }
}
