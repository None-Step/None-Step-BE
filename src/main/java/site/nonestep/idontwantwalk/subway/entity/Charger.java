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
public class Charger {

    @Id
    @Column(name = "charger_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chargerNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "charger_comment", length = 500)
    private String chargerComment;

    @Column(name = "charger_address", length = 500)
    private String chargerAddress;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "charger_latitude")
    private BigDecimal chargerLatitude;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "charger_longitude")
    private BigDecimal chargerLongitude;
}
