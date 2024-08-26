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
public class DownTime {

    @Id
    @Column(name = "down_no")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long downNo;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "line"),
            @JoinColumn(name = "region"),
            @JoinColumn(name = "station")
    })
    private Info info;

    @Column(name = "down_direction", length = 100)
    private String downDirection;

    @Column(name = "down_weekday_start", length = 50)
    private String downWeekdayStart;

    @Column(name = "down_weekday_end", length = 50)
    private String downWeekdayEnd;

    @Column(name = "down_sat_start", length = 50)
    private String downSatStart;

    @Column(name = "down_sat_end", length = 50)
    private String downSatEnd;

    @Column(name = "down_holiday_start", length = 50)
    private String downHolidayStart;

    @Column(name = "down_holiday_end", length = 50)
    private String downHolidayEnd;

}
