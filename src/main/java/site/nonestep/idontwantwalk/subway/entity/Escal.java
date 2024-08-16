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
public class Escal {

    @Id
    @Column(name = "escal_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long escalNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "escal_comment", length = 500)
    private String escalComment;

    @Column(name = "escal_address", length = 500)
    private String escalAddress;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "escal_latitude")
    private BigDecimal escalLatitude;

    @Digits(integer = 5, fraction = 13)
    @Column(name = "escal_Longitude")
    private BigDecimal escalLongitude;

}
