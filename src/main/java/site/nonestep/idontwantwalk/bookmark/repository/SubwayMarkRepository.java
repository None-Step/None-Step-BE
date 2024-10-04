package site.nonestep.idontwantwalk.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.bookmark.entity.SubwayMark;

public interface SubwayMarkRepository extends JpaRepository<SubwayMark,Long>, SubwayMarkRepositoryCustom{
}
