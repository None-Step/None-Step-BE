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
public class NursingRoom {

    @Id
    @Column(name = "nursing_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long nursingNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "nursing_comment", length = 500)
    private String nursingComment;

    @Column(name = "nursing_address", length = 500)
    private String nursingAddress;

    @Digits(integer = 5, fraction = 15)
    @Column(name = "nursing_latitude")
    private BigDecimal nursingLatitude;

    @Digits(integer = 5, fraction = 15)
    @Column(name = "nursing_longitude")
    private BigDecimal nursingLongitude;
}
