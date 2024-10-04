package site.nonestep.idontwantwalk.bookmark.service;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import site.nonestep.idontwantwalk.bookmark.repository.PathMarkRepository;

@Slf4j
@Service
@Transactional
public class PathMarkServiceImpl {

    @Autowired
    private PathMarkRepository pathMarkRepository;
}
