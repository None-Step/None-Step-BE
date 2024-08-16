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
public class Aed{

    @Id
    @Column(name = "aed_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long aedNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "aed_comment", length = 500)
    private String aedComment;

    @Column(name = "aed_address", length = 500)
    private String aedAddress;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "aed_latitude")
    private BigDecimal aedLatitude;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "aed_longitude")
    private BigDecimal aedLongitude;

}
