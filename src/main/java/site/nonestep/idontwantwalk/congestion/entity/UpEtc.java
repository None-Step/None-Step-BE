package site.nonestep.idontwantwalk.congestion.entity;

import jakarta.persistence.*;
import lombok.*;
import site.nonestep.idontwantwalk.subway.entity.Info;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
public class UpEtc {

    @Id
    @Column(name = "up_etc_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long upEtcNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "up_etc_index", length = 50, nullable = false)
    private String upEtcIndex;

    @Column(name = "up_etc_condition", length = 50, nullable = false)
    private String upEtcCondition;

    @Column(name = "up_etc_elevator", length = 200)
    private String upEtcElevator;

    @Column(name = "up_etc_escal", length = 200)
    private String upEtcEscal;

    @Column(name = "up_etc_stair", length = 200)
    private String upEtcStair;

    @Column(name = "up_etc_boarding_best", length = 200)
    private String upEtcBoardingBest;

}
