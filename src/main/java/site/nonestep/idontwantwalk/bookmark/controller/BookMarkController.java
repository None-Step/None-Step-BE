package site.nonestep.idontwantwalk.bookmark.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.bookmark.dto.*;
import site.nonestep.idontwantwalk.bookmark.service.PathMarkService;
import site.nonestep.idontwantwalk.bookmark.service.PlaceMarkService;
import site.nonestep.idontwantwalk.bookmark.service.SubwayMarkService;

import java.util.List;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/book-mark")
public class BookMarkController {

    @Autowired
    private PlaceMarkService placeMarkService;

    @Autowired
    private PathMarkService pathMarkService;

    @Autowired
    private SubwayMarkService subwayMarkService;

    // [장소] 즐겨 찾기 등록
    @PostMapping("/place-register")
    public ResponseEntity<?> placeRegister(@RequestBody PlaceRegisterRequestDTO placeRegisterRequestDTO) {
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        PlaceRegisterResponseDTO placeRegisterResponseDTO = placeMarkService.placeRegister(placeRegisterRequestDTO, memberNo);

        if (placeRegisterResponseDTO == null) {
            return new ResponseEntity<>("즐겨 찾기 등록 갯수 초과입니다. 다시 시도해주세요.", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(placeRegisterResponseDTO, HttpStatus.OK);
        }
    }

    // [장소] 즐겨 찾기 조회
    @GetMapping("/place-list")
    public ResponseEntity<?> placeList() {
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        List<PlaceListResponseDTO> placeList = placeMarkService.placeList(memberNo);

        if (placeList == null) {
            return new ResponseEntity<>("잘못된 접근입니다. 다시 시도하세요", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(placeList, HttpStatus.OK);
        }
    }

    // [장소] 즐겨 찾기 삭제
    @DeleteMapping("/place-delete")
    public ResponseEntity<?> deletePlace(@ModelAttribute PlaceDeleteRequestDTO placeDeleteRequestDTO) {
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        PlaceDeleteResponseDTO placeDeleteResponseDTO = placeMarkService.placeDelete(placeDeleteRequestDTO, memberNo);

        if (placeDeleteResponseDTO == null) {
            return new ResponseEntity<>("잘못된 접근입니다. 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(placeDeleteResponseDTO, HttpStatus.OK);
        }
    }

    // [경로] 즐겨 찾기 등록
    @PostMapping("/path-register")
    public ResponseEntity<?> pathRegister(@RequestBody PathRegisterRequestDTO pathRegisterRequestDTO){
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        PathRegisterResponseDTO pathRegister = pathMarkService.pathRegister(pathRegisterRequestDTO,memberNo);

        if (pathRegister == null) {
            return new ResponseEntity<>("즐겨 찾기 등록 갯수 초과입니다. 다시 시도해주세요.", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(pathRegister, HttpStatus.OK);
        }
    }

    // [경로] 즐겨 찾기 조회
    @GetMapping("/path-list")
    public ResponseEntity<?> pathList(){
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        List<PathListResponseDTO> pathList = pathMarkService.pathList(memberNo);

        if (pathList == null){
            return new ResponseEntity<>("잘못된 접근입니다. 다시 시도하세요", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(pathList, HttpStatus.OK);
        }
    }

    // [경로] 즐겨 찾기 삭제
    @DeleteMapping("/path-delete")
    public ResponseEntity<?> pathDelete(@ModelAttribute PathDeleteRequestDTO pathDeleteRequestDTO) {
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        PathDeleteResponseDTO pathDeleteResponseDTO = pathMarkService.pathDelete(pathDeleteRequestDTO, memberNo);

        if (pathDeleteResponseDTO == null) {
            return new ResponseEntity<>("잘못된 접근입니다. 삭제할 수 없습니다.", HttpStatus.BAD_REQUEST);
        } else {

            return new ResponseEntity<>(pathDeleteResponseDTO, HttpStatus.OK);
        }
    }

}
