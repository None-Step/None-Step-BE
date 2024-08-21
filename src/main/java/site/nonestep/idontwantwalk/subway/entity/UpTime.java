package site.nonestep.idontwantwalk.subway.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpTime {

    @Id
    @Column(name = "up_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long upNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "up_direction", length = 100)
    private String upDirection;

    @Column(name = "up_weekday_start", length = 50)
    private String upWeekdayStart;

    @Column(name = "up_weekday_end", length = 50)
    private String upWeekdayEnd;

    @Column(name = "up_sat_start", length = 50)
    private String upSatStart;

    @Column(name = "up_sat_end", length = 50)
    private String upSatEnd;

    @Column(name = "up_holiday_start", length = 50)
    private String upHolidayStart;

    @Column(name = "up_holiday_end", length = 50)
    private String upHolidayEnd;
}
