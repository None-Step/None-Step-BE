package site.nonestep.idontwantwalk.bookmark.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;
import site.nonestep.idontwantwalk.member.entity.Member;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
// 한 사람이 한 장소를 한 번만 즐겨 찾기 등록하게끔 다중 유니크 설정
// 중복으로 등록하는 것을 방지하기 위함
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "uniqueStartPointAndEndPointAndMemberNo",
                columnNames = {"place_latitude", "place_longitude", "member_no"})
        }
)
@Builder
@AllArgsConstructor
public class PlaceMark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long placeNo;

    @Column(name = "place_nickname", length = 50, nullable = false)
    private String placeNickName;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "place_latitude", nullable = false)
    private BigDecimal placeLatitude;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "place_longitude", nullable = false)
    private BigDecimal placeLongitude;

    @Column(name = "place_color", length = 10, nullable = false)
    private String placeColor;

    @Column(name = "place_address", length = 500, nullable = false)
    private String placeAddress;

    @ManyToOne
    @JoinColumn(name = "member_no", nullable = false)
    private Member memberNo;

}
