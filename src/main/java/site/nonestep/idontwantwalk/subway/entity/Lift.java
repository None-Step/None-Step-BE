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
public class Lift {

    @Id
    @Column(name = "lift_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long liftNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "lift_exit", length = 50)
    private String liftExit;

    @Column(name = "lift_address", length = 500)
    private String liftAddress;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "lift_latitdue")
    private BigDecimal liftLatitude;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "lift_longitude")
    private BigDecimal liftLongitude;

    @Column(name = "lift_start_floor", length = 50)
    private String liftStartFloor;

    @Column(name = "lift_start_comment", length = 500)
    private String liftStartComment;

    @Column(name = "lift_end_floor", length = 50)
    private String liftEndFloor;

    @Column(name = "lift_end_comment", length = 50)
    private String liftEndComment;

    @Column(name = "lift_height", length = 50)
    private String liftHeight;

    @Column(name = "lift_width", length = 50)
    private String liftWidth;

    @Column(name = "lift_kg", length = 50)
    private String liftKG;
}
