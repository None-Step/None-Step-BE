package site.nonestep.idontwantwalk.chat.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ChatRepositoryImpl implements ChatRepositoryCustom{

    @Autowired
    private JPAQueryFactory queryFactory;
}
