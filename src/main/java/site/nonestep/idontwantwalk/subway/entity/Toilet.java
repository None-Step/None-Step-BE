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
public class Toilet {

    @Id
    @Column(name = "toilet_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long toiletNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "toilet_exit", length = 50)
    private String toiletExit;

    @Column(name = "toilet_comment", length = 500)
    private String toiletComment;

    @Column(name = "toilet_address", length = 500)
    private String toiletAddress;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "toilet_latitude")
    private BigDecimal toiletLatitude;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "toilet_longitude")
    private BigDecimal toiletLongitude;
}
