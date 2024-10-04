package site.nonestep.idontwantwalk.bookmark.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@AllArgsConstructor
@IdClass(MemberSubWayBookMark.class)
public class SubwayMark {

    @Id
    @Column(name = "region")
    private String region;

    @Id
    @Column(name = "line")
    private String line;

    @Id
    @Column(name = "station")
    private String station;

    @Id
    @Column(name = "member_no")
    private Long memberNo;

    @Column(name = "subway_mark_nickname", length = 50, nullable = false)
    private String subwayMarkNickName;

}
