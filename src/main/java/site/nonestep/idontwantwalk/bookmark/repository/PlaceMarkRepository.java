package site.nonestep.idontwantwalk.bookmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import site.nonestep.idontwantwalk.bookmark.entity.PlaceMark;

public interface PlaceMarkRepository extends JpaRepository<PlaceMark,Long>, PlaceMarkRepositoryCustom {
}
