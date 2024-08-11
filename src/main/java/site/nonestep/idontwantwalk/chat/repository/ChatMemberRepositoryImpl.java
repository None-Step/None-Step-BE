package site.nonestep.idontwantwalk.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ChatMemberRepositoryImpl implements ChatMemberRepositoryCustom {

    @Autowired
    private JPAQueryFactory queryFactory;
}
