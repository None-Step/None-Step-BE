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
import site.nonestep.idontwantwalk.road.dto.SeoulBikeDTO;
import site.nonestep.idontwantwalk.road.dto.SkResponseDTO;
import site.nonestep.idontwantwalk.road.gsonClass.Features;
import site.nonestep.idontwantwalk.road.gsonClass.Geometry;
import site.nonestep.idontwantwalk.road.gsonClass.GeometryDeserializer;
import site.nonestep.idontwantwalk.road.gsonClass.GoRoad;
import site.nonestep.idontwantwalk.road.jsonClass.RentBikeStatus;
import site.nonestep.idontwantwalk.road.jsonClass.Row;
import site.nonestep.idontwantwalk.road.jsonClass.SeoulBike;
import site.nonestep.idontwantwalk.subway.service.SubwayService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.awt.geom.Point2D.distance;

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

        if (skResponseDTOEscal != null) {
            whatsUrResult.add(skResponseDTOEscal);
        }
        if (skResponseDTOElevator != null) {
            whatsUrResult.add(skResponseDTOElevator);
        }

        whatsUrResult = whatsUrResult.stream().sorted(Comparator.comparing(SkResponseDTO::getDistance)).collect(Collectors.toList());


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
            if (!goRoad.getFeatures().isEmpty()) {
                List<Features> featuresList = goRoad.getFeatures()
                        .stream().filter(o -> o.getGeometry().getType().equals("LineString")).collect(Collectors.toList());
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

        if (skResponseDTOEscal != null) {
            whatsUrResult.add(skResponseDTOEscal);
        }
        if (skResponseDTOElevator != null) {
            whatsUrResult.add(skResponseDTOElevator);
        }

        whatsUrResult = whatsUrResult.stream().sorted(Comparator.comparing(SkResponseDTO::getDistance)).collect(Collectors.toList());

        if (whatsUrResult.isEmpty()) {

            return new ResponseEntity<>("조회할 수 없습니다. 다시 시도해주세요", HttpStatus.BAD_REQUEST);
        } else if (whatsUrResult.get(0).getDistance() >= 5000) {
            return new ResponseEntity<>("조회하려는 거리가 5km 이상입니다.", HttpStatus.BAD_REQUEST);
        } else {

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
            if (!goRoad.getFeatures().isEmpty()) {
                List<Features> featuresList = goRoad.getFeatures()
                        .stream().filter(o -> o.getGeometry().getType().equals("LineString")).collect(Collectors.toList());
                goRoad.setFeatures(featuresList);
            }

            log.info("호출된 API: {}", goRoad);
            return new ResponseEntity<>(goRoad, HttpStatus.OK);
        }
    }

    // 공공데이터 - 서울 자전거 api 쓰기
    @PostMapping("/seoul-bike")
    ResponseEntity<?> seoulBike(@org.springframework.web.bind.annotation.RequestBody SeoulBikeDTO seoulBikeDTO) throws IOException {

        List<Row> seoulRow = new ArrayList<>();

        // 서울 자전거는 한 번에 1000건 까지 밖에 못가져오기 때문에 3번에 걸쳐 받아온다.
        // 서울시 자전거 보관소 갯수 약 2641개로 추정
        for (int i = 1; i <= 1800; i += 900) {

            OkHttpClient client = new OkHttpClient();

            // 서울시 자전거 API 호출
            Request request = new Request.Builder()
                    .url("http://openapi.seoul.go.kr:8088/" + authConfig.getSeoulbike() + "/json/bikeList/" + i + "/" + (i + 900) + "/")
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();

            String changeResponse = response.body().string();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(changeResponse);

            JsonObject seoulBike = element.getAsJsonObject();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            SeoulBike seoul = gson.fromJson(seoulBike, SeoulBike.class);

            seoulRow.addAll(seoul.getRentBikeStatus().getRow());
        }

        // 보관소에 거치된 자전거가 1개 이상인 경우만 뜨게끔 filter를 건다.
        seoulRow = seoulRow.stream().filter(o -> Integer.parseInt(o.getParkingBikeTotCnt()) >= 1).collect(Collectors.toList());

        // 그 후, 사용자 위치에서 가장 가까운 자전거 보관소를 추출하기 위해 정렬한다.
        Collections.sort(seoulRow, new Comparator<Row>() {
            @Override
            public int compare(Row o1, Row o2) {

                double whereIsMyBike = distance(Double.parseDouble(o1.getStationLatitude()), Double.parseDouble(o1.getStationLongitude())
                        , seoulBikeDTO.getCurrentLatitude(), seoulBikeDTO.getCurrentLongitude());

                double whereIsMyBike2 = distance(Double.parseDouble(o2.getStationLatitude()), Double.parseDouble(o2.getStationLongitude())
                        , seoulBikeDTO.getCurrentLatitude(), seoulBikeDTO.getCurrentLongitude());

                if (whereIsMyBike > whereIsMyBike2) {
                    return 1;
                } else if (whereIsMyBike < whereIsMyBike2) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });

        // 현재 위치에서 역 까지 도보순으로 계산 후 비교하기 위해 선언 후 채워줌
        // 도보: 현재 위치 > 역의 거리와  현재 위치 > 자전거 보관소의 거리를 비교한다.
        // 비교 후, 도보로 목적지 역 까지의 거리가 더 멀면 띄우지 않기 위해서
        GoStationRequestDTO goStationRequestDTO = new GoStationRequestDTO();
        goStationRequestDTO.setGoStation(seoulBikeDTO.getGoStation());
        goStationRequestDTO.setGoRegion(seoulBikeDTO.getGoRegion());
        goStationRequestDTO.setCurrentLatitude(new BigDecimal(""+seoulBikeDTO.getCurrentLatitude()));
        goStationRequestDTO.setCurrentLongitude(new BigDecimal(""+seoulBikeDTO.getCurrentLongitude()));

        // 현재 위치 > 역 까지의 거리를 도보로도 계산한다.
        SkResponseDTO skResponseDTOtotalStation = subwayService.walkStation(goStationRequestDTO);
        double homeToStation =  skResponseDTOtotalStation.getDistance();
        double homeToBike = distance(Double.parseDouble(seoulRow.get(0).getStationLatitude()) ,Double.parseDouble(seoulRow.get(0).getStationLongitude())
                                    , seoulBikeDTO.getCurrentLatitude(), seoulBikeDTO.getCurrentLongitude());

        // 도보로 가는게 더 빠르면 BAD_REQUEST를 보낸다.
        if (homeToStation <= homeToBike){
            return new ResponseEntity<>("자전거 보관소 까지의 거리가 도보로 역까지의 거리의 절반보다 멉니다.", HttpStatus.BAD_REQUEST);
        }else{
        // 자전거 보관소가 더 가까울 경우 SK API를 이용해 보관소까지의 도보 거리를 보낸다
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\"startX\":"
                    + goStationRequestDTO.getCurrentLongitude() + ",\"startY\":" + goStationRequestDTO.getCurrentLatitude() +
                    ",\"endX\":" + seoulRow.get(0).getStationLongitude() + ",\"endY\":" + seoulRow.get(0).getStationLatitude() +
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
            if (!goRoad.getFeatures().isEmpty()) {
                List<Features> featuresList = goRoad.getFeatures()
                        .stream().filter(o -> o.getGeometry().getType().equals("LineString")).collect(Collectors.toList());
                goRoad.setFeatures(featuresList);
            }

            log.info("호출된 API: {}", goRoad);
            return new ResponseEntity<>(goRoad, HttpStatus.OK);

        }
    }

    public static double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) +
                Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));

        // dist의 값이 범위를 벗어나는 경우를 처리
        if (dist > 1.0) {
            dist = 1.0;
        } else if (dist < -1.0) {
            dist = -1.0;
        }

        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515 * 1609.344;

        return dist; //단위 meter
    }

    // 10진수를 radian(라디안)으로 변환
    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    //radian(라디안)을 10진수로 변환
    private static double rad2deg(double rad) {
        return (rad * 180 / Math.PI);
    }

}
