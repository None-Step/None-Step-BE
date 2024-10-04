package site.nonestep.idontwantwalk.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.bookmark.entity.PathMark;

public interface PathMarkRepository extends JpaRepository<PathMark,Long>, PathMarkRepositoryCustom{
}
