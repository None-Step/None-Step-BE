package site.nonestep.idontwantwalk.subway.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class DifToilet {

    @Id
    @Column(name = "dif_toilet_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long difToiletNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "dif_toilet_exit", length = 50)
    private String difToiletExit;

    @Column(name = "dif_toilet_comment", length = 500)
    private String difToiletComment;

    @Column(name = "dif_toilet_address", length = 500)
    private String difToiletAddress;

    @Digits(integer = 5, fraction = 15)
    @Column(name = "dif_toilet_latitude")
    private BigDecimal difToiletLatitude;

    @Digits(integer = 5, fraction = 15)
    @Column(name = "dif_toilet_longitude")
    private BigDecimal difToiletLongitude;
}
