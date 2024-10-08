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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "uniquePathAndMemberNo",
                columnNames = {"path_start_latitude", "path_start_longitude", "path_end_latitude", "path_end_longitude", "member_no"}
                )
        }
)
public class PathMark {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pathNo;

    @Column(name = "path_start_nickname", length = 50, nullable = false)
    private String pathStartNickName;

    @Column(name = "path_end_nickname", length = 50, nullable = false)
    private String pathEndNickName;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "path_start_latitude", nullable = false)
    private BigDecimal pathStartLatitude;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "path_start_longitude", nullable = false)
    private BigDecimal pathStartLongitude;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "path_end_latitude", nullable = false)
    private BigDecimal pathEndLatitude;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "path_end_longitude", nullable = false)
    private BigDecimal pathEndLongitude;

    @Column(name = "path_color", length = 10,nullable = false)
    private String pathColor;

    @ManyToOne
    @JoinColumn(name = "member_no", nullable = false)
    private Member memberNo;

}
