package site.nonestep.idontwantwalk.congestion.entity;

import jakarta.persistence.*;
import lombok.*;
import site.nonestep.idontwantwalk.subway.entity.Info;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class DownEtc {

    @Id
    @Column(name = "down_etc_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long downEtcNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "down_etc_index", length = 50, nullable = false)
    private String downEtcIndex;

    @Column(name = "down_etc_condition", length = 50, nullable = false)
    private String downEtcCondition;

    @Column(name = "down_etc_elevator", length = 200)
    private String downEtcElevator;

    @Column(name = "down_etc_escal", length = 200)
    private String downEtcEscal;

    @Column(name = "down_etc_stair", length = 200)
    private String downEtcStair;

    @Column(name = "down_etc_boarding_best", length = 200)
    private String downEtcBoardingBest;
}
