package site.nonestep.idontwantwalk.member.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "UniqueMemberNicknameAndRandom",//다중유니크값의 이름을 적어주고
                columnNames = {"member_nickname","member_random" })//다중유니크로 걸어줄 행의 값을 적어준다.
        }
)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Builder

public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberNo;

    @Column(name = "member_id", length = 100, unique = true)
    private String memberID;

    @Column(name = "member_name", length = 100, nullable = false)
    private String memberName;

    @Column(name = "member_mail", length = 100, nullable = false)
    private String memberMail;

    @Column(name = "member_password", length = 2000, nullable = false)
    private String memberPassword;

    @Column(name = "member_phone", length = 100, nullable = false)
    private String memberPhone;

    @Column(name = "member_file", length = 3000)
    private String memberFile;

    @Enumerated(EnumType.STRING)
    @Column(name = "member_social_type")
    private SocialType memberSocialType;

    @Column(name = "member_social_id", length = 500)
    private String memberSocialID;

    @Column(name = "member_refresh_token", length = 2000)
    private String memberRefreshToken;

    @Column(name = "member_nickName", length = 100, nullable = false)
    private String memberNickName;

    @Column(name = "member_random", length = 100, nullable = false)
    private String memberRandom;

    @Column(name = "member_jointime")
    private LocalDateTime memberJoinTime;

    @Builder.Default
    @Column(name = "member_isdelete")
    private Boolean memberIsDelete = false;

    @Column(name = "member_delete_time")
    private LocalDateTime memberDeleteTime;

    @Column(name = "member_report")
    private Integer memberReport; //신고횟수

    // 일반 & 소셜 로그인 시, Refresh Token DB 저장
    public void  changeToken(String memberRefreshToken){
        this.memberRefreshToken = memberRefreshToken;
    }

    //프로필변경: 휴대폰
    public void modifyPhone(String memberPhone){
        this.memberPhone = memberPhone;
    }

    //프로필변경: 메일
    public void modifyMail(String memberMail){
        this.memberMail = memberMail;
    }

    //프로필변경: 비밀먼호
    public void modifyPass(String memberPassword){
        this.memberPassword = memberPassword;
    }

    //프로필변경: 이미지, 닉네임
    public void  modifyNickName(String memberNickName, String memberFile ){
        if (memberNickName != null){
            this.memberNickName = memberNickName;
        }

        if (memberFile != null){
            this.memberFile = memberFile;
        }// 오류가날 수 있기 때문에 각각 나눠서 해준다.
    }

    //회원탈퇴
    public void delete(Boolean memberIsDelete ){
        this.memberIsDelete = memberIsDelete;
    }
}
