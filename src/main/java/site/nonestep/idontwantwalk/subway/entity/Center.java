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
public class Center {

    @Id
    @Column(name = "center_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long centerNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "center_comment", length = 500)
    private String centerComment;

    @Column(name = "center_address", length = 500)
    private String centerAddress;

    @Column(name = "center_hours", length = 500)
    private String centerHours;

    @Column(name = "center_tel", length = 100)
    private String centerTel;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "center_latitude")
    private BigDecimal centerLatitude;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "center_longitude")
    private BigDecimal centerLongitude;
}
