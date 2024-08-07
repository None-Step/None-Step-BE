package site.nonestep.idontwantwalk.member.controller;

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
import org.springframework.security.core.context.SecurityContextHolder;
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

    //pw 찾기
    @PostMapping("/pwfind")
    public ResponseEntity<?> pwfind(@RequestBody MemberPwFindRequestDTO memberPwFindRequestDTO){
        MemberPwFindResponseDTO memberPwFindResponseDTO = memberService.pwFind(memberPwFindRequestDTO);

        if (memberPwFindResponseDTO == null){
            return new ResponseEntity<>("일치하는 정보가 없습니다.", HttpStatus.BAD_REQUEST);
        }else{
             return new ResponseEntity<>(memberPwFindResponseDTO, HttpStatus.OK);
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
            // Cookie는 코드 구현이 필요 없다. Chrome 등 exp에서 가지고 있다가 자동으로 보내준다(크롬이)
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

    //로그아웃
    @GetMapping("/logout")
    public ResponseEntity<?> logout(){
        MultiValueMap<String, String> headers = new HttpHeaders(); //새로 선언
        MemberLogoutResponseDTO memberLogoutResponseDTO = new MemberLogoutResponseDTO();
        memberLogoutResponseDTO.setMessage("success");
        //이제 보내야하는데 그 전에 토큰을 제거해준다.
        ResponseCookie cookie = ResponseCookie.from("Refresh")
                .sameSite("None")
                .secure(true)
                .path("/")
                .maxAge(0)//즉시 제거해
                .build();
        headers.add("Set-Cookie", cookie.toString());

        //이제 토큰 제거할 준비다됨

        return new ResponseEntity<>(memberLogoutResponseDTO, headers, HttpStatus.OK);


    }

    //마이페이지 조회
    @GetMapping("/info")
    public ResponseEntity<?> info(){
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()); //나 토큰을 가져오겠다. 쓰겠어!
        MemberInfoResponseDTO memberInfoResponseDTO = memberService.info(memberNo);
        if (memberInfoResponseDTO == null){
            return new ResponseEntity<>("잘못된 정보입니다.", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(memberInfoResponseDTO, HttpStatus.OK);
        }
    }

    //다른유저 프로필 조회
    @PostMapping("/others")
    public ResponseEntity<?> others(@RequestBody MemberOthersRequestDTO memberOthersRequestDTO){
        //나도 로그인이 되어 있는 상태기 때문에 token을 가지고 있다.
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()); //나 토큰을 가져오겠다. 쓰겠어!
        MemberOthersResponseDTO memberOthersResponseDTO = memberService.others(memberOthersRequestDTO);
        if (memberOthersResponseDTO == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            return new ResponseEntity<>(memberOthersResponseDTO, HttpStatus.OK);
        }
    }

    //프로필변경:휴대폰
    @PutMapping("/modify-phone")
    public ResponseEntity<?> modifyPhone(@RequestBody MemberModifyPhoneRequestDTO memberModifyPhoneRequestDTO){
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        MemberModifyPhoneResponseDTO memberModifyPhoneResponseDTO = memberService.modifyPhone(memberModifyPhoneRequestDTO, memberNo);
        return new ResponseEntity<>(memberModifyPhoneResponseDTO, HttpStatus.OK);
    }

    //프로필변경: 메일
    @PutMapping("/modify-mail")
    public ResponseEntity<?> modifyMail(@RequestBody MemberModifyMailRequestDTO memberModifyMailRequestDTO){
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        MemberModifyMailResponseDTO memberModifyMailResponseDTO = memberService.modifyMail(memberModifyMailRequestDTO, memberNo);
        return new ResponseEntity<>(memberModifyMailResponseDTO ,HttpStatus.OK);
    }

    //프로필변경: 비밀번호
    @PutMapping("/modify-pass")
    public ResponseEntity<?> modifyPass(@RequestBody MemberModifyPassRequestDTO memberModifyPassRequestDTO){
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        MemberModifyPassResponseDTO memberModifyPassResponseDTO = memberService.modifyPass(memberModifyPassRequestDTO, memberNo);
        return new ResponseEntity<>(memberModifyPassResponseDTO ,HttpStatus.OK);
    }

    //프로필변경: 닉네임 이름 및 이미지 변경
    @PutMapping("/modify-nickname")
    public ResponseEntity<?> modifyNickName(@ModelAttribute MemberModifyNickNameRequestDTO memberModifyNickNameRequestDTO){
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        MemberModifyNickNameResponseDTO memberModifyNickNameResponseDTO = memberService.modifyNick(memberModifyNickNameRequestDTO, memberNo);
        return new ResponseEntity<>(memberModifyNickNameResponseDTO ,HttpStatus.OK);
    }

    //회원탈퇴
    @PostMapping("/delete")
    public ResponseEntity<?> delete(){
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        MemberDeleteResponseDTO memberDeleteResponseDTO = memberService.delete(memberNo);
        return new ResponseEntity<>(memberDeleteResponseDTO ,HttpStatus.OK);
    }

    // Access Token 값 만료 되었을 경우 Refresh Token 전달 후 새로운 Access Token 생성
    @PostMapping("/token")
    public ResponseEntity<?> accessToken(@RequestBody SendTokenRequestDTO sendTokenRequestDTO) {
        log.info("{}", sendTokenRequestDTO);

        Long newAccessToken = memberService.isRefreshTokenAndIdOk(sendTokenRequestDTO);

        if (newAccessToken == null) {
            return new ResponseEntity<>("잘못된 처리입니다. 다시 로그인 해주세요.", HttpStatus.BAD_REQUEST);
        } else {
            JsonWebToken jsonWebToken = JwtTokenUtils.allocateToken(newAccessToken, "ROLE_USER");
            MultiValueMap<String, String> headers = new HttpHeaders();
            headers.add("Authorization", jsonWebToken.getAccessToken());

            SendTokenResponseDTO sendTokenResponseDTO = new SendTokenResponseDTO();
            sendTokenResponseDTO.setMessage("Success");

            log.info("{}", sendTokenResponseDTO);
            return new ResponseEntity<>(sendTokenResponseDTO, headers, HttpStatus.OK);
        }
    }
}
