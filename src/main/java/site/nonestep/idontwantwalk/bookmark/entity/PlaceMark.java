package site.nonestep.idontwantwalk.bookmark.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.*;
import site.nonestep.idontwantwalk.member.entity.Member;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
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
