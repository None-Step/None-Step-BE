package site.nonestep.idontwantwalk.bookmark.repository;

public interface PlaceMarkRepositoryCustom {

    // [장소] 즐겨 찾기 등록 하기 전, 5개가 넘는지 확인하기 위해 조회
    Long selectPlaceMarkCount(Long memberNo);

}
