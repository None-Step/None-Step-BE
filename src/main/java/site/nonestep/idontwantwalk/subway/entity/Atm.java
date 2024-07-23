package site.nonestep.idontwantwalk.subway.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Atm {

    @Id
    @Column(name = "atm_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long atmNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "atm_comment", length = 500)
    private String atmComment;

    @Column(name = "atm_address", length = 500)
    private String atmAddress;

    @Digits(integer = 5, fraction = 15)
    @Column(name = "atm_latitude")
    private BigDecimal atmLatitude;

    @Digits(integer = 5, fraction = 15)
    @Column(name = "atm_longitude")
    private BigDecimal atmLongitude;
}
