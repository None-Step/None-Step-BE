package site.nonestep.idontwantwalk.bookmark.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.bookmark.dto.PlaceRegisterRequestDTO;
import site.nonestep.idontwantwalk.bookmark.dto.PlaceRegisterResponseDTO;
import site.nonestep.idontwantwalk.bookmark.service.PlaceMarkService;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/book-mark")
public class BookMarkController {

    @Autowired
    private PlaceMarkService placeMarkService;

    @PostMapping("/place-register")
    public ResponseEntity<?> placeRegister(@RequestBody PlaceRegisterRequestDTO placeRegisterRequestDTO){
        Long memberNo = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());

        PlaceRegisterResponseDTO placeRegisterResponseDTO = placeMarkService.placeRegister(placeRegisterRequestDTO, memberNo);

        if (placeRegisterResponseDTO == null){
            return new ResponseEntity<>("즐겨 찾기 등록 갯수 초과입니다. 다시 시도해주세요.", HttpStatus.BAD_REQUEST);
        }else{
            return new ResponseEntity<>(placeRegisterResponseDTO, HttpStatus.OK);
        }
    }


}
