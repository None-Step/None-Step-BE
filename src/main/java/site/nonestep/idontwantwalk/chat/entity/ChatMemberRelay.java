package site.nonestep.idontwantwalk.chat.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class ChatMemberRelay {

    private Long memberNo;
    private Long chatRoomNo;
}
