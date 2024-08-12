package site.nonestep.idontwantwalk.road.controller;

import com.nimbusds.jose.shaded.gson.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import site.nonestep.idontwantwalk.config.AuthConfig;
import site.nonestep.idontwantwalk.road.dto.GoRoadRequestDTO;
import site.nonestep.idontwantwalk.road.dto.GoStationRequestDTO;
import site.nonestep.idontwantwalk.road.dto.SkResponseDTO;
import site.nonestep.idontwantwalk.road.gsonClass.Features;
import site.nonestep.idontwantwalk.road.gsonClass.Geometry;
import site.nonestep.idontwantwalk.road.gsonClass.GeometryDeserializer;
import site.nonestep.idontwantwalk.road.gsonClass.GoRoad;
import site.nonestep.idontwantwalk.subway.service.SubwayService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/road")
public class RoadController {

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private SubwayService subwayService;

    // 현재 위치 > 역 까지 도보 API
    @PostMapping("/go-station")
    public ResponseEntity<?> goStation(@org.springframework.web.bind.annotation.RequestBody GoStationRequestDTO goStationRequestDTO) throws IOException {
        log.info("{}", goStationRequestDTO);
        SkResponseDTO skResponseDTOtotalStation = subwayService.walkStation(goStationRequestDTO);
        SkResponseDTO skResponseDTOEscal = subwayService.nearByEscal(goStationRequestDTO);
        SkResponseDTO skResponseDTOElevator = subwayService.nearByElevator(goStationRequestDTO);

        List<SkResponseDTO> whatsUrResult = new ArrayList<>();
        if (skResponseDTOtotalStation != null) {
            whatsUrResult.add(skResponseDTOtotalStation);
        }

        log.info("{}", 11111111);
        if (skResponseDTOEscal != null) {
            whatsUrResult.add(skResponseDTOEscal);
        }
        log.info("{}", 222222);
        if (skResponseDTOElevator != null) {
            whatsUrResult.add(skResponseDTOElevator);
        }
        log.info("{}", 3333333);

        whatsUrResult = whatsUrResult.stream().sorted(Comparator.comparing(SkResponseDTO::getDistance)).collect(Collectors.toList());
        log.info("{}", 4444444);

        if (whatsUrResult.isEmpty()) {

            return new ResponseEntity<>("조회할 수 없습니다. 다시 시도해주세요", HttpStatus.BAD_REQUEST);
        } else if (whatsUrResult.get(0).getDistance() >= 5000) {
            return new ResponseEntity<>("조회하려는 거리가 5km 이상입니다.", HttpStatus.BAD_REQUEST);
        } else {

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"startX\":"
                    + goStationRequestDTO.getCurrentLongitude() + ",\"startY\":" + goStationRequestDTO.getCurrentLatitude() +
                    ",\"endX\":" + whatsUrResult.get(0).getLongitude() + ",\"endY\":" + whatsUrResult.get(0).getLatitude() +
                    ",\"reqCoordType\":\"WGS84GEO\",\"startName\":\"현재 위치\",\"endName\":\"" + goStationRequestDTO.getGoStation() + "\"," +
                    "\"searchOption\":\"30\",\"resCoordType\":\"WGS84GEO\",\"sort\":\"index\"}");
            Request request = new Request.Builder()
                    .url("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&callback=function")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .addHeader("appKey", authConfig.getSk())
                    .build();

            Response response = client.newCall(request).execute();

            String changeResponse = response.body().string();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(changeResponse);

            JsonObject features = element.getAsJsonObject();

//            Gson gson = new Gson();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Geometry.class, new GeometryDeserializer());
            Gson gson = gsonBuilder.create();
            GoRoad goRoad = gson.fromJson(features, GoRoad.class);
            if(!goRoad.getFeatures().isEmpty()){
                List<Features> featuresList = goRoad.getFeatures()
                        .stream().filter(o -> o.getGeometry().getType().equals("LineString")  ).collect(Collectors.toList());
                goRoad.setFeatures(featuresList);
            }

            log.info("호출된 API: {}", goRoad);
            return new ResponseEntity<>(goRoad, HttpStatus.OK);
        }
    }

    // 길 찾기 (역 > 목적지)
    @PostMapping("/go-road")
    ResponseEntity<?> goRoad(@org.springframework.web.bind.annotation.RequestBody GoRoadRequestDTO goRoadRequestDTO) throws IOException {

        log.info("호출된 API: {}", goRoadRequestDTO);

        SkResponseDTO skResponseDTOtotalStation = subwayService.goRoadStation(goRoadRequestDTO);
        SkResponseDTO skResponseDTOEscal = subwayService.goRoadEscal(goRoadRequestDTO);
        SkResponseDTO skResponseDTOElevator = subwayService.goRoadElevator(goRoadRequestDTO);

        List<SkResponseDTO> whatsUrResult = new ArrayList<>();
        if (skResponseDTOtotalStation != null) {
            whatsUrResult.add(skResponseDTOtotalStation);
        }

        log.info("{}", 11111111);
        if (skResponseDTOEscal != null) {
            whatsUrResult.add(skResponseDTOEscal);
        }
        log.info("{}", 222222);
        if (skResponseDTOElevator != null) {
            whatsUrResult.add(skResponseDTOElevator);
        }
        log.info("{}", 3333333);

        whatsUrResult = whatsUrResult.stream().sorted(Comparator.comparing(SkResponseDTO::getDistance)).collect(Collectors.toList());
        log.info("{}", 4444444);

        if (whatsUrResult.isEmpty()) {

            return new ResponseEntity<>("조회할 수 없습니다. 다시 시도해주세요", HttpStatus.BAD_REQUEST);
        } else if (whatsUrResult.get(0).getDistance() >= 5000) {
            return new ResponseEntity<>("조회하려는 거리가 5km 이상입니다.", HttpStatus.BAD_REQUEST);
        }else{

            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"startX\":"
                    + whatsUrResult.get(0).getLongitude() + ",\"startY\":" + whatsUrResult.get(0).getLatitude() +
                    ",\"endX\":" + goRoadRequestDTO.getGoLongitude() + ",\"endY\":" + goRoadRequestDTO.getGoLatitude() +
                    ",\"reqCoordType\":\"WGS84GEO\",\"startName\":\"" + goRoadRequestDTO.getCurrentStation() + "\",\"endName\":\"" + "도착지" + "\"," +
                    "\"searchOption\":\"30\",\"resCoordType\":\"WGS84GEO\",\"sort\":\"index\"}");
            Request request = new Request.Builder()
                    .url("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&callback=function")
                    .post(body)
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .addHeader("appKey", authConfig.getSk())
                    .build();

            Response response = client.newCall(request).execute();

            String changeResponse = response.body().string();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(changeResponse);

            JsonObject features = element.getAsJsonObject();

//            Gson gson = new Gson();
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeAdapter(Geometry.class, new GeometryDeserializer());
            Gson gson = gsonBuilder.create();
            GoRoad goRoad = gson.fromJson(features, GoRoad.class);
            if(!goRoad.getFeatures().isEmpty()){
                List<Features> featuresList = goRoad.getFeatures()
                        .stream().filter(o -> o.getGeometry().getType().equals("LineString")  ).collect(Collectors.toList());
                goRoad.setFeatures(featuresList);
            }

            log.info("호출된 API: {}", goRoad);
            return new ResponseEntity<>(goRoad, HttpStatus.OK);
        }
    }

}
