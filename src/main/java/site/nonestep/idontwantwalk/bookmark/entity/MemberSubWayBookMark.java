package site.nonestep.idontwantwalk.bookmark.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class MemberSubWayBookMark {

    private String line;
    private String region;
    private String station;
    private Long memberNo;
}
