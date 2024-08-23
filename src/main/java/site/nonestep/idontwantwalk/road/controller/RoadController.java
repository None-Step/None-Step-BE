package site.nonestep.idontwantwalk.road.controller;

import com.nimbusds.jose.shaded.gson.*;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.RequestBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.HTTP;
import site.nonestep.idontwantwalk.config.AuthConfig;
import site.nonestep.idontwantwalk.road.dto.*;
import site.nonestep.idontwantwalk.road.list.SkBikePath;
import site.nonestep.idontwantwalk.road.service.RoadService;
import site.nonestep.idontwantwalk.road.sk.Features;
import site.nonestep.idontwantwalk.road.sk.Geometry;
import site.nonestep.idontwantwalk.road.sk.GeometryDeserializer;
import site.nonestep.idontwantwalk.road.sk.GoRoad;
import site.nonestep.idontwantwalk.road.bike.DaejeonBike;
import site.nonestep.idontwantwalk.road.bike.DaejeonRow;
import site.nonestep.idontwantwalk.road.bike.Row;
import site.nonestep.idontwantwalk.road.bike.SeoulBike;
import site.nonestep.idontwantwalk.road.subwaypath.Path;
import site.nonestep.idontwantwalk.subway.service.SubwayService;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
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

    @Autowired
    private RoadService roadService;

    // 현재 위치 > 역 까지 도보 API
    @PostMapping("/go-station")
    public ResponseEntity<?> goStation(@org.springframework.web.bind.annotation.RequestBody GoStationRequestDTO goStationRequestDTO) throws IOException {
        log.info("호출된 API: {}", goStationRequestDTO);

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
        } else if (whatsUrResult.get(0).getDistance() >= 40000) {
            return new ResponseEntity<>("조회하려는 거리가 40km 이상입니다.", HttpStatus.BAD_REQUEST);
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
        } else if (whatsUrResult.get(0).getDistance() >= 40000) {
            return new ResponseEntity<>("조회하려는 거리가 40km 이상입니다.", HttpStatus.BAD_REQUEST);
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
        log.info("호출된 API: {}", seoulBikeDTO);
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
        goStationRequestDTO.setCurrentLatitude(new BigDecimal("" + seoulBikeDTO.getCurrentLatitude()));
        goStationRequestDTO.setCurrentLongitude(new BigDecimal("" + seoulBikeDTO.getCurrentLongitude()));

        // 현재 위치 > 역 까지의 거리를 도보로도 계산한다.
        SkResponseDTO skResponseDTOtotalStation = subwayService.walkStation(goStationRequestDTO);
        double homeToStation = skResponseDTOtotalStation.getDistance();
        double homeToBike = distance(Double.parseDouble(seoulRow.get(0).getStationLatitude()), Double.parseDouble(seoulRow.get(0).getStationLongitude())
                , seoulBikeDTO.getCurrentLatitude(), seoulBikeDTO.getCurrentLongitude());

        // 도보로 가는게 더 빠르면 BAD_REQUEST를 보낸다.
        if (skResponseDTOtotalStation == null) {

            return new ResponseEntity<>("조회할 수 없습니다. 다시 시도해주세요", HttpStatus.BAD_REQUEST);
        } else if (homeToStation <= homeToBike) {
            return new ResponseEntity<>("자전거 보관소 까지의 거리가 도보로 역까지의 거리보다 멉니다.", HttpStatus.BAD_REQUEST);
        } else {


            GoRoad pathFromHomeToBike = skApiMethod(Double.toString(seoulBikeDTO.getCurrentLatitude()),Double.toString(seoulBikeDTO.getCurrentLongitude())
                    ,seoulRow.get(0).getStationLatitude(), seoulRow.get(0).getStationLongitude());

            GoRoad pathFromBikeToStation = skApiMethod(seoulRow.get(0).getStationLatitude(), seoulRow.get(0).getStationLongitude()
                    ,skResponseDTOtotalStation.getLatitude().toString(),skResponseDTOtotalStation.getLongitude().toString());

            pathFromHomeToBike.getFeatures().addAll( pathFromBikeToStation.getFeatures());
            log.info("호출된 API: {}", pathFromHomeToBike);
            return new ResponseEntity<>(pathFromHomeToBike, HttpStatus.OK);
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

    // 대전 자전거 API
    @PostMapping("daejeon-bike")
    public ResponseEntity<?> daejeonBike(@org.springframework.web.bind.annotation.RequestBody DaejeonBikeDTO daejeonBikeDTO) throws IOException {

        log.info("호출된 API: {}", daejeonBikeDTO);
        OkHttpClient client = new OkHttpClient();

        // 대전시 자전거 API 호출
        Request request = new Request.Builder()
                .url("https://bikeapp.tashu.or.kr:50041/v1/openapi/station")
                .addHeader("api-token", authConfig.getDaejeonbike())
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();

        String changeResponse = response.body().string();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(changeResponse);

        JsonObject daejeonBike = element.getAsJsonObject();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        DaejeonBike daejeon = gson.fromJson(daejeonBike, DaejeonBike.class);

        List<DaejeonRow> daejeonRow = daejeon.getResults();

        // 보관소에 거치된 자전거가 1개 이상인 경우만 뜨게끔 filter를 건다.
        daejeonRow = daejeonRow.stream().filter(o -> o.getParking_count() >= 1).collect(Collectors.toList());

        // 그 후, 사용자 위치에서 가장 가까운 자전거 보관소를 추출하기 위해 정렬한다.
        Collections.sort(daejeonRow, new Comparator<DaejeonRow>() {
            @Override
            public int compare(DaejeonRow o1, DaejeonRow o2) {

                double whereIsMyBike = distance(Double.parseDouble(o1.getX_pos()), Double.parseDouble(o1.getY_pos())
                        , daejeonBikeDTO.getCurrentLatitude(), daejeonBikeDTO.getCurrentLongitude());

                double whereIsMyBike2 = distance(Double.parseDouble(o2.getX_pos()), Double.parseDouble(o2.getY_pos())
                        , daejeonBikeDTO.getCurrentLatitude(), daejeonBikeDTO.getCurrentLongitude());

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
        goStationRequestDTO.setGoStation(daejeonBikeDTO.getGoStation());
        goStationRequestDTO.setGoRegion(daejeonBikeDTO.getGoRegion());
        goStationRequestDTO.setCurrentLatitude(new BigDecimal("" + daejeonBikeDTO.getCurrentLatitude()));
        goStationRequestDTO.setCurrentLongitude(new BigDecimal("" + daejeonBikeDTO.getCurrentLongitude()));

        // 현재 위치 > 역 까지의 거리를 도보로도 계산한다.
        SkResponseDTO skResponseDTOtotalStation = subwayService.walkStation(goStationRequestDTO);
        double homeToStation = skResponseDTOtotalStation.getDistance();
        double homeToBike = distance(Double.parseDouble(daejeonRow.get(0).getX_pos()), Double.parseDouble(daejeonRow.get(0).getY_pos())
                , daejeonBikeDTO.getCurrentLatitude(), daejeonBikeDTO.getCurrentLongitude());

        // 도보로 가는게 더 빠르면 BAD_REQUEST를 보낸다.
        if (skResponseDTOtotalStation == null) {

            return new ResponseEntity<>("조회할 수 없습니다. 다시 시도해주세요", HttpStatus.BAD_REQUEST);
        } else if (homeToStation <= homeToBike) {
            return new ResponseEntity<>("자전거 보관소 까지의 거리가 도보로 역까지의 거리보다 멉니다.", HttpStatus.BAD_REQUEST);
        } else {

            GoRoad pathFromHomeToBike = skApiMethod(Double.toString(daejeonBikeDTO.getCurrentLatitude()),Double.toString(daejeonBikeDTO.getCurrentLongitude())
                    ,daejeonRow.get(0).getX_pos(), daejeonRow.get(0).getY_pos());

            GoRoad pathFromBikeToStation = skApiMethod(daejeonRow.get(0).getX_pos(), daejeonRow.get(0).getY_pos()
                    ,skResponseDTOtotalStation.getLatitude().toString(),skResponseDTOtotalStation.getLongitude().toString());

            pathFromHomeToBike.getFeatures().addAll( pathFromBikeToStation.getFeatures());
            log.info("호출된 API: {}", pathFromHomeToBike);
            return new ResponseEntity<>(pathFromHomeToBike, HttpStatus.OK);


        }
    }

    // 지하철 경로(환승) API
    @PostMapping("/subway-path")
    public ResponseEntity<?> subwayPath(@org.springframework.web.bind.annotation.RequestBody SubwayPathRequestDTO subwayPathRequestDTO) throws IOException {

        log.info("호출된 API: {}", subwayPathRequestDTO);
        OkHttpClient client = new OkHttpClient();

        SubwayPathResponseDTOX start = roadService.selectCidAndSid(subwayPathRequestDTO.getRegion(), subwayPathRequestDTO.getStartLine(), subwayPathRequestDTO.getStartStation());
        SubwayPathResponseDTOX end = roadService.selectCidAndSid(subwayPathRequestDTO.getRegion(), subwayPathRequestDTO.getEndLine(), subwayPathRequestDTO.getEndStation());

        if (start == null || end == null) {
            return new ResponseEntity<>("잘못 입력 되었습니다. 다시 시도해주세요", HttpStatus.BAD_REQUEST);
        } else {
            log.info("{}", start);
//             obsay API 호출
            Request request = new Request.Builder()
                    .url("https://api.odsay.com/v1/api/subwayPath?apiKey=" + authConfig.getOdsay() + "&CID=" + start.getCid()
                            + "&SID=" + start.getSid() + "&EID=" + end.getSid() + "&lang=0")
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .build();
            log.info("{}" , end);
            Response response = client.newCall(request).execute();

            String changeResponse = response.body().string();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(changeResponse);

            JsonObject subwayPath = element.getAsJsonObject();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            Path path = gson.fromJson(subwayPath, Path.class);


            log.info("호출된 API: {}", path);
            return new ResponseEntity<>(path, HttpStatus.OK);

        }
    }

    // 이동 수단에 관한 목록(도보 & 자전거)
    @PostMapping("/go-list")
    public ResponseEntity<?> goList(@org.springframework.web.bind.annotation.RequestBody GoListRequestDTO goListRequestDTO) throws IOException {

        log.info("호출된 API: {}", goListRequestDTO);
        StationListDTOX stationListDTOX = subwayService.station(goListRequestDTO);
        List<GoListResponseDTO> goListResponseDTOList = new ArrayList<>();
        if (stationListDTOX == null){
            return new ResponseEntity<>("잘못된 접근입니다. 다시 시도하세요", HttpStatus.BAD_REQUEST);
        }

        OkHttpClient walkClient = new OkHttpClient();

        MediaType walkMediaType = MediaType.parse("application/json");
        RequestBody walkBody = RequestBody.create(walkMediaType, "{\"startX\":"
                + goListRequestDTO.getCurrentLongitude()
                + ",\"startY\":" +  goListRequestDTO.getCurrentLatitude()+
                ",\"endX\":" + stationListDTOX.getLongitude() + ",\"endY\":" + stationListDTOX.getLatitude() +
                ",\"reqCoordType\":\"WGS84GEO\",\"startName\":\"현재 위치\",\"endName\":\"" + goListRequestDTO.getGoStation() + "\"," +
                "\"searchOption\":\"30\",\"resCoordType\":\"WGS84GEO\",\"sort\":\"index\"}");
        Request walkRequest = new Request.Builder()
                .url("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&callback=function")
                .post(walkBody)
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .addHeader("appKey", authConfig.getSk())
                .build();

        Response walkResponse = walkClient.newCall(walkRequest).execute();

        String changeWalkResponse = walkResponse.body().string();
        JsonParser walkParser = new JsonParser();
        JsonElement walkElement = walkParser.parse(changeWalkResponse);

        JsonObject walkFeatures = walkElement.getAsJsonObject();

        GsonBuilder walkGsonBuilder = new GsonBuilder();
        Gson walkGson = walkGsonBuilder.create();
        SkBikePath walkPath = walkGson.fromJson(walkFeatures, SkBikePath.class);

        GoListResponseDTO walkGoListResponseDTO = new GoListResponseDTO();
        walkGoListResponseDTO.setType("WALK");
        walkGoListResponseDTO.setTime(walkPath.getFeatures().get(0).getProperties().getTotalTime());
        walkGoListResponseDTO.setDistance(walkPath.getFeatures().get(0).getProperties().getTotalDistance());

        goListResponseDTOList.add(walkGoListResponseDTO);




        if (goListRequestDTO.getGoRegion().equals("수도권")){

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
                            , goListRequestDTO.getCurrentLatitude(), goListRequestDTO.getCurrentLongitude());

                    double whereIsMyBike2 = distance(Double.parseDouble(o2.getStationLatitude()), Double.parseDouble(o2.getStationLongitude())
                            , goListRequestDTO.getCurrentLatitude(), goListRequestDTO.getCurrentLongitude());

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
            goStationRequestDTO.setGoStation(goListRequestDTO.getGoStation());
            goStationRequestDTO.setGoRegion(goListRequestDTO.getGoRegion());
            goStationRequestDTO.setCurrentLatitude(new BigDecimal("" + goListRequestDTO.getCurrentLatitude()));
            goStationRequestDTO.setCurrentLongitude(new BigDecimal("" + goListRequestDTO.getCurrentLongitude()));

            // 현재 위치 > 역 까지의 거리를 도보로도 계산한다.
            SkResponseDTO skResponseDTOtotalStation = subwayService.walkStation(goStationRequestDTO);
            double homeToStation = skResponseDTOtotalStation.getDistance();
            double homeToBike = distance(Double.parseDouble(seoulRow.get(0).getStationLatitude()), Double.parseDouble(seoulRow.get(0).getStationLongitude())
                    , goListRequestDTO.getCurrentLatitude(), goListRequestDTO.getCurrentLongitude());

            // 도보로 가는게 더 빠르면 BAD_REQUEST를 보낸다.
            if (skResponseDTOtotalStation == null) {

                return new ResponseEntity<>("조회할 수 없습니다. 다시 시도해주세요", HttpStatus.BAD_REQUEST);
            } else if (homeToStation <= homeToBike) {
                return new ResponseEntity<>("자전거 보관소 까지의 거리가 도보로 역까지의 거리보다 멉니다.", HttpStatus.BAD_REQUEST);
            } else {
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

                GsonBuilder gsonBuilder = new GsonBuilder();
                gsonBuilder.registerTypeAdapter(Geometry.class, new GeometryDeserializer());
                Gson gson = gsonBuilder.create();
                SkBikePath skBikePath = gson.fromJson(features, SkBikePath.class);

                GoListResponseDTO goListResponseDTO = new GoListResponseDTO();
                goListResponseDTO.setType("BIKE");
                goListResponseDTO.setTime(skBikePath.getFeatures().get(0).getProperties().getTotalTime());
                goListResponseDTO.setDistance(skBikePath.getFeatures().get(0).getProperties().getTotalDistance());

                if (!skBikePath.getFeatures().isEmpty()) {
                    List<site.nonestep.idontwantwalk.road.list.Features> featuresList = skBikePath.getFeatures()
                            .stream().filter(o -> o.getGeometry().getType().equals("LineString")).collect(Collectors.toList());
                    skBikePath.setFeatures(featuresList);
                }

                // ❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤
                // ❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤
                // 이제 자전거 보관소 > 역
                OkHttpClient bikeClient = new OkHttpClient();
                ArrayList<Double> list = (ArrayList<Double>) skBikePath.getFeatures().get(skBikePath.getFeatures().size()-1).getGeometry().getCoordinates().get(0);
                double[] doubles = list.stream().mapToDouble(Double::doubleValue).toArray();

                MediaType bikeMediaType = MediaType.parse("application/json");
                RequestBody bikeBody = RequestBody.create(bikeMediaType, "{\"startX\":"
                        + doubles[0]
                        + ",\"startY\":" +  doubles[1]  +
                        ",\"endX\":" + stationListDTOX.getLongitude() + ",\"endY\":" + stationListDTOX.getLatitude() +
                        ",\"reqCoordType\":\"WGS84GEO\",\"startName\":\"현재 위치\",\"endName\":\"" + goStationRequestDTO.getGoStation() + "\"," +
                        "\"searchOption\":\"30\",\"resCoordType\":\"WGS84GEO\",\"sort\":\"index\"}");
                Request bikeRequest = new Request.Builder()
                        .url("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&callback=function")
                        .post(bikeBody)
                        .addHeader("accept", "application/json")
                        .addHeader("content-type", "application/json")
                        .addHeader("appKey", authConfig.getSk())
                        .build();

                Response bikeResponse = bikeClient.newCall(bikeRequest).execute();

                String changeBikeResponse = bikeResponse.body().string();
                JsonParser bikeParser = new JsonParser();
                JsonElement bikeElement = bikeParser.parse(changeBikeResponse);

                JsonObject bikeFeatures = bikeElement.getAsJsonObject();

                GsonBuilder bikeGsonBuilder = new GsonBuilder();
                Gson bikeGson = bikeGsonBuilder.create();
                SkBikePath skBikePath2 = bikeGson.fromJson(bikeFeatures, SkBikePath.class);

                goListResponseDTO.setTime((skBikePath2.getFeatures().get(0).getProperties().getTotalTime() / 4) + goListResponseDTO.getTime());
                goListResponseDTO.setDistance(skBikePath2.getFeatures().get(0).getProperties().getTotalDistance() + goListResponseDTO.getDistance());

                goListResponseDTOList.add(goListResponseDTO);
                log.info("호출된 API: {}", goListResponseDTOList);
                return new ResponseEntity<>(goListResponseDTOList, HttpStatus.OK);


            }
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        // 이제 대전 할 거임~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        } else if (goListRequestDTO.getGoRegion().equals("대전")) {
            OkHttpClient client = new OkHttpClient();

            // 대전시 자전거 API 호출
            Request request = new Request.Builder()
                    .url("https://bikeapp.tashu.or.kr:50041/v1/openapi/station")
                    .addHeader("api-token", authConfig.getDaejeonbike())
                    .addHeader("accept", "application/json")
                    .addHeader("content-type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();

            String changeResponse = response.body().string();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(changeResponse);

            JsonObject daejeonBike = element.getAsJsonObject();

            GsonBuilder gsonBuilder = new GsonBuilder();
            Gson gson = gsonBuilder.create();
            DaejeonBike daejeon = gson.fromJson(daejeonBike, DaejeonBike.class);

            List<DaejeonRow> daejeonRow = daejeon.getResults();

            // 보관소에 거치된 자전거가 1개 이상인 경우만 뜨게끔 filter를 건다.
            daejeonRow = daejeonRow.stream().filter(o -> o.getParking_count() >= 1).collect(Collectors.toList());

            // 그 후, 사용자 위치에서 가장 가까운 자전거 보관소를 추출하기 위해 정렬한다.
            Collections.sort(daejeonRow, new Comparator<DaejeonRow>() {
                @Override
                public int compare(DaejeonRow o1, DaejeonRow o2) {

                    double whereIsMyBike = distance(Double.parseDouble(o1.getX_pos()), Double.parseDouble(o1.getY_pos())
                            , goListRequestDTO.getCurrentLatitude(), goListRequestDTO.getCurrentLongitude());

                    double whereIsMyBike2 = distance(Double.parseDouble(o2.getX_pos()), Double.parseDouble(o2.getY_pos())
                            , goListRequestDTO.getCurrentLatitude(), goListRequestDTO.getCurrentLongitude());

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
            goStationRequestDTO.setGoStation(goListRequestDTO.getGoStation());
            goStationRequestDTO.setGoRegion(goListRequestDTO.getGoRegion());
            goStationRequestDTO.setCurrentLatitude(new BigDecimal("" + goListRequestDTO.getCurrentLatitude()));
            goStationRequestDTO.setCurrentLongitude(new BigDecimal("" + goListRequestDTO.getCurrentLongitude()));

            // 현재 위치 > 역 까지의 거리를 도보로도 계산한다.
            SkResponseDTO skResponseDTOtotalStation = subwayService.walkStation(goStationRequestDTO);
            double homeToStation = skResponseDTOtotalStation.getDistance();
            double homeToBike = distance(Double.parseDouble(daejeonRow.get(0).getX_pos()), Double.parseDouble(daejeonRow.get(0).getY_pos())
                    , goListRequestDTO.getCurrentLatitude(), goListRequestDTO.getCurrentLongitude());

            // 도보로 가는게 더 빠르면 BAD_REQUEST를 보낸다.
            if (skResponseDTOtotalStation == null) {

                return new ResponseEntity<>("조회할 수 없습니다. 다시 시도해주세요", HttpStatus.BAD_REQUEST);
            } else if (homeToStation <= homeToBike) {
                return new ResponseEntity<>("자전거 보관소 까지의 거리가 도보로 역까지의 거리보다 멉니다.", HttpStatus.BAD_REQUEST);
            } else {
                // 자전거 보관소가 더 가까울 경우 SK API를 이용해 보관소까지의 도보 거리를 보낸다
                OkHttpClient daeClient = new OkHttpClient();

                MediaType mediaType = MediaType.parse("application/json");
                RequestBody body = RequestBody.create(mediaType, "{\"startX\":"
                        + goStationRequestDTO.getCurrentLongitude() + ",\"startY\":" + goStationRequestDTO.getCurrentLatitude() +
                        ",\"endX\":" + daejeonRow.get(0).getY_pos() + ",\"endY\":" + daejeonRow.get(0).getX_pos() +
                        ",\"reqCoordType\":\"WGS84GEO\",\"startName\":\"현재 위치\",\"endName\":\"" + goStationRequestDTO.getGoStation() + "\"," +
                        "\"searchOption\":\"30\",\"resCoordType\":\"WGS84GEO\",\"sort\":\"index\"}");
                Request daeRequest = new Request.Builder()
                        .url("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&callback=function")
                        .post(body)
                        .addHeader("accept", "application/json")
                        .addHeader("content-type", "application/json")
                        .addHeader("appKey", authConfig.getSk())
                        .build();

                Response daeResponse = daeClient.newCall(daeRequest).execute();

                String changeDaeResponse = daeResponse.body().string();
                JsonParser daeParser = new JsonParser();
                JsonElement daeElement = daeParser.parse(changeDaeResponse);

                JsonObject features = daeElement.getAsJsonObject();

                GsonBuilder daeGsonBuilder = new GsonBuilder();
                daeGsonBuilder.registerTypeAdapter(Geometry.class, new GeometryDeserializer());
                Gson daeGson = daeGsonBuilder.create();
                SkBikePath skBikePath = daeGson.fromJson(features, SkBikePath.class);

                GoListResponseDTO goListResponseDTO = new GoListResponseDTO();
                goListResponseDTO.setType("BIKE");
                goListResponseDTO.setTime(skBikePath.getFeatures().get(0).getProperties().getTotalTime());
                goListResponseDTO.setDistance(skBikePath.getFeatures().get(0).getProperties().getTotalDistance());

                if (!skBikePath.getFeatures().isEmpty()) {
                    List<site.nonestep.idontwantwalk.road.list.Features> featuresList = skBikePath.getFeatures()
                            .stream().filter(o -> o.getGeometry().getType().equals("LineString")).collect(Collectors.toList());
                    skBikePath.setFeatures(featuresList);
                }

                // ❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤
                // ❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤❤
                // 이제 자전거 보관소 > 역
                OkHttpClient bikeClient = new OkHttpClient();
                ArrayList<Double> list = (ArrayList<Double>) skBikePath.getFeatures().get(skBikePath.getFeatures().size()-1).getGeometry().getCoordinates().get(0);
                double[] doubles = list.stream().mapToDouble(Double::doubleValue).toArray();

                MediaType bikeMediaType = MediaType.parse("application/json");
                RequestBody bikeBody = RequestBody.create(bikeMediaType, "{\"startX\":"
                        + doubles[0]
                        + ",\"startY\":" + doubles[1]  +
                        ",\"endX\":" + stationListDTOX.getLongitude() + ",\"endY\":" + stationListDTOX.getLatitude() +
                        ",\"reqCoordType\":\"WGS84GEO\",\"startName\":\"현재 위치\",\"endName\":\"" + goStationRequestDTO.getGoStation() + "\"," +
                        "\"searchOption\":\"30\",\"resCoordType\":\"WGS84GEO\",\"sort\":\"index\"}");
                Request bikeRequest = new Request.Builder()
                        .url("https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&callback=function")
                        .post(bikeBody)
                        .addHeader("accept", "application/json")
                        .addHeader("content-type", "application/json")
                        .addHeader("appKey", authConfig.getSk())
                        .build();

                Response bikeResponse = bikeClient.newCall(bikeRequest).execute();

                String changeBikeResponse = bikeResponse.body().string();
                JsonParser bikeParser = new JsonParser();
                JsonElement bikeElement = bikeParser.parse(changeBikeResponse);

                JsonObject bikeFeatures = bikeElement.getAsJsonObject();

                GsonBuilder bikeGsonBuilder = new GsonBuilder();
                Gson bikeGson = bikeGsonBuilder.create();
                SkBikePath skBikePath2 = bikeGson.fromJson(bikeFeatures, SkBikePath.class);

                goListResponseDTO.setTime((skBikePath2.getFeatures().get(0).getProperties().getTotalTime() / 4) + goListResponseDTO.getTime());
                goListResponseDTO.setDistance(skBikePath2.getFeatures().get(0).getProperties().getTotalDistance() + goListResponseDTO.getDistance());

                goListResponseDTOList.add(goListResponseDTO);
                log.info("호출된 API: {}", goListResponseDTOList);
                return new ResponseEntity<>(goListResponseDTOList, HttpStatus.OK);
            }
        }else{

            log.info("호출된 API: {}", goListResponseDTOList);
            return new ResponseEntity<>(goListResponseDTOList,HttpStatus.OK);
        }
    }

    // 자전거 보관소의 위치를 알려주는 API
    @GetMapping("/bike-marker")
    public ResponseEntity<?> bikeMarker(@ModelAttribute BikeMarkerDTO bikeMarkerDTO) throws IOException {
        log.info("호출된 API: {}", bikeMarkerDTO);
        BikeMarkerDTO daejeon = daejeonBike(bikeMarkerDTO);
        BikeMarkerDTO seoul = seoulBike(bikeMarkerDTO);
        if(daejeon == null && seoul == null) {
            return new ResponseEntity<>("오류가 발생하였습니다.", HttpStatus.BAD_REQUEST);
        }else if(daejeon == null){
            return new ResponseEntity<>(seoul,HttpStatus.OK);
        }else if(seoul == null){
            return new ResponseEntity<>(daejeon,HttpStatus.OK);
        }

        double daejeonLength = distance(daejeon.getLatitude().doubleValue(), daejeon.getLongitude().doubleValue()
                , bikeMarkerDTO.getLatitude().doubleValue(),bikeMarkerDTO.getLongitude().doubleValue());

        double seoulLength = distance(seoul.getLatitude().doubleValue(), seoul.getLongitude().doubleValue()
                , bikeMarkerDTO.getLatitude().doubleValue(),bikeMarkerDTO.getLongitude().doubleValue());

        log.info("호출된 API: {}", daejeonLength > seoulLength ? seoul : daejeon );
       return new ResponseEntity<>(daejeonLength > seoulLength ? seoul : daejeon  ,HttpStatus.OK);

    }

    public BikeMarkerDTO daejeonBike(BikeMarkerDTO bikeMarkerDTO) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // 대전시 자전거 API 호출
        Request request = new Request.Builder()
                .url("https://bikeapp.tashu.or.kr:50041/v1/openapi/station")
                .addHeader("api-token", authConfig.getDaejeonbike())
                .addHeader("accept", "application/json")
                .addHeader("content-type", "application/json")
                .build();

        Response response = client.newCall(request).execute();

        String changeResponse = response.body().string();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(changeResponse);

        JsonObject daejeonBike = element.getAsJsonObject();

        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        DaejeonBike daejeon = gson.fromJson(daejeonBike, DaejeonBike.class);

        List<DaejeonRow> daejeonRow = daejeon.getResults();

        // 보관소에 거치된 자전거가 1개 이상인 경우만 뜨게끔 filter를 건다.
        daejeonRow = daejeonRow.stream().filter(o -> o.getParking_count() >= 1).collect(Collectors.toList());

        // 그 후, 사용자 위치에서 가장 가까운 자전거 보관소를 추출하기 위해 정렬한다.
        daejeonRow.sort(new Comparator<DaejeonRow>() {
            @Override
            public int compare(DaejeonRow o1, DaejeonRow o2) {

                double whereIsMyBike = distance(Double.parseDouble(o1.getX_pos()), Double.parseDouble(o1.getY_pos())
                        , bikeMarkerDTO.getLatitude().doubleValue(), bikeMarkerDTO.getLongitude().doubleValue());

                double whereIsMyBike2 = distance(Double.parseDouble(o2.getX_pos()), Double.parseDouble(o2.getY_pos())
                        , bikeMarkerDTO.getLatitude().doubleValue(), bikeMarkerDTO.getLongitude().doubleValue());

                return Double.compare(whereIsMyBike, whereIsMyBike2);
            }
        });

        if(daejeonRow.isEmpty()){
            return null;
        }

        return new BikeMarkerDTO(new BigDecimal(daejeonRow.get(0).getX_pos()),new BigDecimal(daejeonRow.get(0).getY_pos()));
    }

    public BikeMarkerDTO seoulBike(BikeMarkerDTO bikeMarkerDTO) throws IOException {
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
                        , bikeMarkerDTO.getLatitude().doubleValue(),bikeMarkerDTO.getLongitude().doubleValue());

                double whereIsMyBike2 = distance(Double.parseDouble(o2.getStationLatitude()), Double.parseDouble(o2.getStationLongitude())
                        , bikeMarkerDTO.getLatitude().doubleValue(),bikeMarkerDTO.getLongitude().doubleValue());

                return Double.compare(whereIsMyBike, whereIsMyBike2);
            }
        });


        if(seoulRow.isEmpty()){
            return null;
        }

        return new BikeMarkerDTO(new BigDecimal(seoulRow.get(0).getStationLatitude()),new BigDecimal(seoulRow.get(0).getStationLongitude()));
    }
    
    
    public GoRoad skApiMethod(String startLatitude, String startLongitude,String endLatitude, String endLongitude) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\"startX\":"
                + startLongitude + ",\"startY\":" + startLatitude +
                ",\"endX\":" + endLongitude + ",\"endY\":" + endLatitude +
                ",\"reqCoordType\":\"WGS84GEO\",\"startName\":\"" + "출발지" + "\",\"endName\":\"" + "도착지" + "\"," +
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

        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(Geometry.class, new GeometryDeserializer());
        Gson gson = gsonBuilder.create();
        GoRoad goRoad = gson.fromJson(features, GoRoad.class);
        if (!goRoad.getFeatures().isEmpty()) {
            List<Features> featuresList = goRoad.getFeatures()
                    .stream().filter(o -> o.getGeometry().getType().equals("LineString")).collect(Collectors.toList());
            goRoad.setFeatures(featuresList);
        }

        return goRoad;
    }
}
