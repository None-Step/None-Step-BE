package site.nonestep.idontwantwalk.bookmark.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.bookmark.repository.SubwayMarkRepository;

@Slf4j
@Service
@Transactional
public class SubwayMarkServiceImpl {

    @Autowired
    private SubwayMarkRepository subwayMarkRepository;
}
