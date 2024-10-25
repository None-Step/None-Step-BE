package site.nonestep.idontwantwalk.weather.controller;


import com.nimbusds.jose.shaded.gson.*;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.config.AuthConfig;
import site.nonestep.idontwantwalk.road.service.RoadService;
import site.nonestep.idontwantwalk.subway.service.SubwayService;
import site.nonestep.idontwantwalk.weather.dto.CurrentWeatherRequestDTO;
import site.nonestep.idontwantwalk.weather.dto.WeatherRequestDTO;
import site.nonestep.idontwantwalk.weather.dto.WeatherResponseDTO;

import java.io.File;
import java.lang.reflect.Type;


import java.io.IOException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@RestController
@CrossOrigin("*")
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private AuthConfig authConfig;

    @Autowired
    private RoadService roadService;

    @Autowired
    private SubwayService subwayService;

    // 실시간 날씨(초단기예보)
    @PostMapping("/current")
    public ResponseEntity<?> current(@RequestBody WeatherRequestDTO weatherRequestDTO) throws IOException {
        // 데이터를 캐싱하기 위한 Size 선언
        List<WeatherResponseDTO> top3MinTimes = new ArrayList<>();
        int cacheSize = 10 * 1024 * 1024;

        // 캐싱된 data를 저장할 곳
        // 폴더가 없을 경우 자동으로 java.io.tmpdir 경로에 "weatherCache" 폴더를 생성한다.
        // OkHttp가 자동으로 캐시가 다 차면 삭제한다.
        File cacheDirectory = new File(System.getProperty("java.io.tmpdir"), "weatherCache");

        // cacheDirectory 존재하지 않을 경우 생성
        if (!cacheDirectory.exists()) {
            cacheDirectory.mkdirs();
        }

        // 캐싱에 저장이 용이하게 baseTime은 시간만 맞추고 분은 00으로 고정한다.
        // 기상청 API에서는 한 시간 단위로 값을 보내주기 때문이다.
        DateTimeFormatter zoneTimeFormat = DateTimeFormatter.ofPattern("HH00");

        // 기상청 API에서 요구하는 baseTime(시간대)로 형식을 맞춰준다.
        DateTimeFormatter zoneDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

        // UTC로 설정 되어있으므로, 우리 시간에 맞춰 9시간을 더한다.
        ZoneOffset zoneOffSet = ZoneOffset.of("+09:00");
        OffsetDateTime date = OffsetDateTime.now(zoneOffSet);

        // 사용자가 호출하는 시간이 18시 30분 이라면 18시부터 날씨를 보여주기 위해 한 시간을 빼준다.
        date = date.minusHours(1);

        String baseTime = date.format(zoneTimeFormat);
        String baseDate = date.format(zoneDateFormat);


        // 캐시 설정(위치, 사이즈)
        Cache cache = new Cache(cacheDirectory, cacheSize);

        // 호출할 기상청 API
        String url = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?";
        url += "serviceKey=" + authConfig.getWeather();
        url += "&pageNo=1&numOfRows=1000&dataType=JSON";
        url += "&base_date=" + baseDate;
        url += "&base_time=" + baseTime;
        url += "&nx=" + weatherRequestDTO.getX();
        url += "&ny=" + weatherRequestDTO.getY();
        log.info("url : {}", url);

        // new OkHttpClient() > new OkHttpClient.Builder()로 변환
        // cache를 넣기 위해 builder()로 변경함
        // 5초가 지나면 timeout error를 띄운다. (기본 10 > 5s로 변경)
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .readTimeout(5, TimeUnit.SECONDS)
                .connectTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(url).build();

        // 기상청 api 호출
        try (Response response = client.newCall(request).execute()) {

            // String으로 결과 수신한다.
            // 결과를 execute로 실행한다. > String으로 body에 있는 data만 받아온다. > 그 후, JsonParser를 통해 역직렬화 한다.

            String changeResponse = response.body().string();
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(changeResponse);

            // 결과를 역직렬화한다.
            JsonObject jsonObject = element.getAsJsonObject();
            jsonObject = jsonObject.get("response").getAsJsonObject();
            jsonObject = jsonObject.get("body").getAsJsonObject();
            jsonObject = jsonObject.get("items").getAsJsonObject();
            JsonArray jsonArray = jsonObject.get("item").getAsJsonArray();

            Gson gson = new Gson();
            Type personListType = new TypeToken<List<WeatherResponseDTO>>() {
            }.getType();
            List<WeatherResponseDTO> result = gson.fromJson(jsonArray, personListType);
            result = result.stream().filter(this::isCheckWeatherCode).collect(Collectors.toList());

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

            Map<String, List<WeatherResponseDTO>> groupedByCategory = result.stream()
                    .collect(Collectors.groupingBy(WeatherResponseDTO::getCategory)); // category로 그룹화

            top3MinTimes = groupedByCategory.values().stream()
                    .flatMap(group -> group.stream()
                            .sorted((o1, o2) -> {
                                LocalDate date1 = LocalDate.parse(o1.getFcstDate(), dateFormatter);
                                LocalDate date2 = LocalDate.parse(o2.getFcstDate(), dateFormatter);
                                if (date1.equals(date2)) {
                                    return LocalTime.parse(o1.getFcstTime(), timeFormatter).compareTo(LocalTime.parse(o2.getFcstTime(), timeFormatter));
                                } else {
                                    return LocalDate.parse(o1.getFcstDate(), dateFormatter).compareTo(LocalDate.parse(o2.getFcstDate(), dateFormatter));
                                }

                            })
                            // fcstTime을 LocalTime으로 변환 후 비교
                            .limit(3)) // 상위 3개만 선택
                    .collect(Collectors.toList());
        } finally {
            if (cache != null) {
                cache.close();  // Ensure the cache is properly closed
            }
        }

        return new ResponseEntity<>(top3MinTimes, HttpStatus.OK);
    }

    // filter는 무조건 T/F값을 return 하기 때문에 boolean 함수를 새로 만든다.
    // 그 후, 필요한 코드값만 switch문을 통해 받아온다. 나머지는 false값을 줘서 걸러준다.
    public boolean isCheckWeatherCode(WeatherResponseDTO weatherResponseDTO) {
        switch (weatherResponseDTO.getCategory()) {
            case "T1H":
            case "RN1":
            case "SKY":
            case "PTY":
            case "LGT":
            case "WSD":
                return true;
            default:
                return false;
        }
    }

    // 사용자의 위도, 경도를 바탕으로 실시간 기상청 초단기예보 API 호출하는 것
    @PostMapping("/current-weather")
    public ResponseEntity<?> currentWeather(@RequestBody CurrentWeatherRequestDTO currentWeatherRequestDTO) throws IOException {
        // 지구 반경(km)을 정의한다. 지구 반경은 약 6371km이다.
        double earthRadius = 6371.00877;

        // 격자 간격(km)을 정의한다. 기상청 간격이 5km인 것으로 추측하기 때문에 5로 설정한다.
        double cellSize = 5.0;

        // 투영 위도1(degree)
        // 람베르트 정각 원추 투영법에서 사용하는 첫 번째 표준 위도를 뜻한다.
        // 표준 위도는 보통 지도에서 중간 지역의 왜곡을 줄이기 위해 해당 지역에 맞는 값으로 설정된다.
        // 한국의 위도 범위가 대략 33도에서 38도 사이에 있으므로, 첫 번째 표준 위도로 30도를 설정하여 이 지역에서 지도 투영 시 왜곡을 줄이는 것이다.
        double standardLatitude1 = 30.0;

        // 투영 위도2(degree)
        // 투영의 범위를 넓게 설정하고 특정 지역에서의 정확도를 높이기 위해 사용한다.
        double standardLatitude2 = 60.0;

        // 기준점 경도(degree)
        // 한국의 경도는 124 ~ 130 사이에 위치하고 있다. 126은 서울을 비롯한 대한민국의 중앙에 가까운 경도이다.
        double originalLongitude = 126.0;

        // 기준점 위도(degree)
        // 위도 38도는 한국의 중부 지역을 지나며, 특히 서울과 인천 등이 이 위도에 가까운 지역에 위치해 있다.
        // 이 값을 기준점으로 설정하는 이유는 한국을 중심으로 한 지리적 투영을 할 때 왜곡을 최소화하고 정확한 격자 좌표 변환을 수행하기 위함입니다.
        // 이 기준점은 투영 좌표계에서 중심 역할을 하며, 주어진 위도와 경도 값들이 이 기준점으로부터 얼마만큼 떨어져 있는지 계산한다.
        double originalLatitude = 38.0;

        // 기준점 X좌표(GRID)
        double positionX = 43;

        // 기준점 Y좌표(GRID)
        double positionY = 136;

        // 각도를 라디안으로 변환하기 위한 변수
        double deToRad = Math.PI / 180.0;

        // 지구 반경을 격자 간격으로 나눈 값
        double scaledEarthRadius = earthRadius / cellSize;

        // 첫 번째 표준 위도를 라디안으로 변환한 값
        double firstStandardLatitudeRad = standardLatitude1 * deToRad;

        // 두 번째 표준 위도를 라디안으로 변환한 값
        double secondStandardLatitudeRad = standardLatitude2 * deToRad;

        // 기준 경도를 라디안으로 변환한 값
        double baseLongitudeInRad = originalLongitude * deToRad;

        // 기준 위도를 라디안으로 변환한 값
        double baseLatitudeInRad = originalLatitude * deToRad;

        // 투영을 위한 계산의 일부로 사용되는 값
        double standardLatitudeRatio = Math.tan(Math.PI * 0.25 + secondStandardLatitudeRad * 0.5) / Math.tan(Math.PI * 0.25 + firstStandardLatitudeRad * 0.5);
        standardLatitudeRatio = Math.log(Math.cos(firstStandardLatitudeRad) / Math.cos(secondStandardLatitudeRad)) / Math.log(standardLatitudeRatio);

        // 첫 번째 표준 위도를 사용하여 계산된 값
        double firstLatFactor = Math.tan(Math.PI * 0.25 + firstStandardLatitudeRad * 0.5);
        firstLatFactor = Math.pow(firstLatFactor, standardLatitudeRatio) * Math.cos(firstStandardLatitudeRad) / standardLatitudeRatio;

        // 기준 위도를 사용하여 계산된 값
        double baseLatFactor = Math.tan(Math.PI * 0.25 + baseLatitudeInRad * 0.5);
        baseLatFactor = scaledEarthRadius * firstLatFactor / Math.pow(baseLatFactor, standardLatitudeRatio);

        // currentWeatherRequestDTO에서 위도, 경도를 가져와 lat_X, lat_Y라는 변수에 double 형태로 저장한다.
        double lat_X = currentWeatherRequestDTO.getLatitude().doubleValue();
        double lng_Y = currentWeatherRequestDTO.getLongitude().doubleValue();

        // 위도 값을 사용하여 계산된 값
        double tanLatX = Math.tan(Math.PI * 0.25 + (lat_X) * deToRad * 0.5);
        tanLatX = scaledEarthRadius * firstLatFactor / Math.pow(tanLatX, standardLatitudeRatio);

        // 경도 값을 사용하여 계산된 값
        double thetaValue = lng_Y * deToRad - baseLongitudeInRad;

        // 값이 양수일 때
        if (thetaValue > Math.PI) {
            thetaValue -= 2.0 * Math.PI;
        }

        // 값이 음수일 때
        if (thetaValue < -Math.PI) {
            thetaValue += 2.0 * Math.PI;
        }

        thetaValue *= standardLatitudeRatio;

        // 기상청 x, y 값에 넣어줄 것을 각각 int로 변환한다.
        int x = (int) Math.floor(tanLatX * Math.sin(thetaValue) + positionX + 0.5);
        int y = (int) Math.floor(baseLatFactor - tanLatX * Math.cos(thetaValue) + positionY + 0.5);

        // 데이터를 캐싱하기 위한 Size 선언
        List<WeatherResponseDTO> top3MinTimes = new ArrayList<>();
        int cacheSize = 10 * 1024 * 1024;

        // 캐싱된 data를 저장할 곳
        // 폴더가 없을 경우 자동으로 java.io.tmpdir 경로에 "weatherCache" 폴더를 생성한다.
        // OkHttp가 자동으로 캐시가 다 차면 삭제한다.
        File cacheDirectory = new File(System.getProperty("java.io.tmpdir"), "weatherCache");

        // cacheDirectory 존재하지 않을 경우 생성
        if (!cacheDirectory.exists()) {
            cacheDirectory.mkdirs();
        }

        // 캐싱에 저장이 용이하게 baseTime은 시간만 맞추고 분은 00으로 고정한다.
        // 기상청 API에서는 한 시간 단위로 값을 보내주기 때문이다.
        DateTimeFormatter zoneTimeFormat = DateTimeFormatter.ofPattern("HH00");

        // 기상청 API에서 요구하는 baseTime(시간대)로 형식을 맞춰준다.
        DateTimeFormatter zoneDateFormat = DateTimeFormatter.ofPattern("yyyyMMdd");

        // UTC로 설정 되어있으므로, 우리 시간에 맞춰 9시간을 더한다.
        ZoneOffset zoneOffSet = ZoneOffset.of("+09:00");
        OffsetDateTime date = OffsetDateTime.now(zoneOffSet);

        // 사용자가 호출하는 시간이 18시 30분 이라면 18시부터 날씨를 보여주기 위해 한 시간을 빼준다.
        date = date.minusHours(1);

        String baseTime = date.format(zoneTimeFormat);
        String baseDate = date.format(zoneDateFormat);


        // 캐시 설정(위치, 사이즈)
        Cache cache = new Cache(cacheDirectory, cacheSize);

        // 호출할 기상청 API
        String url = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?";
        url += "serviceKey=" + authConfig.getWeather();
        url += "&pageNo=1&numOfRows=1000&dataType=JSON";
        url += "&base_date=" + baseDate;
        url += "&base_time=" + baseTime;
        url += "&nx=" + x;
        url += "&ny=" + y;
        log.info("url : {}", url);

        // new OkHttpClient() > new OkHttpClient.Builder()로 변환
        // cache를 넣기 위해 builder()로 변경함
        // 5초가 지나면 timeout error를 띄운다. (기본 10 > 5s로 변경)
        OkHttpClient client = new OkHttpClient.Builder()
                .cache(cache)
                .readTimeout(2, TimeUnit.SECONDS)
                .connectTimeout(2, TimeUnit.SECONDS)
                .writeTimeout(2, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder().url(url).build();

        Response response = null;
        for (int i = 0; i < 5; i++){
            // 현재 서버에서 timeout error가 계속 발생하고 있기 때문에
            // 에러가 나지 않으면 호출되고 isSuccessWeather가 true인 상태에서 break;
            // isSuccessWeather가 false이면 호출 error가 났으므로 다시 for문을 돌려 호출한다.
            boolean isSuccessWeather = true;

            // 기상청 api 호출
            try {
                response = client.newCall(request).execute();
            } catch (Exception e) {
                isSuccessWeather = false;
            }

            if (isSuccessWeather == true){
                break;
            }
        }

        // for문 끝나고 캐시가 null이 아니라면 캐시 저장을 끝낸다.
        // 기존 try-catch-finally 구문에서 finally에 넣고 무조건 닫게 했으나
        // 로직 변경으로 for문에 날씨 호출을 넣을 것이므로 따로 빼준다.
        if (cache != null) {
            cache.close();  // Ensure the cache is properly closed
        }

        // String으로 결과 수신한다.
        // 결과를 execute로 실행한다. > String으로 body에 있는 data만 받아온다. > 그 후, JsonParser를 통해 역직렬화 한다.
        String changeResponse = response.body().string();
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(changeResponse);

        // 결과를 역직렬화한다.
        JsonObject jsonObject = element.getAsJsonObject();
        jsonObject = jsonObject.get("response").getAsJsonObject();
        jsonObject = jsonObject.get("body").getAsJsonObject();
        jsonObject = jsonObject.get("items").getAsJsonObject();
        JsonArray jsonArray = jsonObject.get("item").getAsJsonArray();

        Gson gson = new Gson();
        Type personListType = new TypeToken<List<WeatherResponseDTO>>() {
        }.getType();
        List<WeatherResponseDTO> result = gson.fromJson(jsonArray, personListType);
        result = result.stream().filter(this::isCheckWeatherCode).collect(Collectors.toList());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        Map<String, List<WeatherResponseDTO>> groupedByCategory = result.stream()
                .collect(Collectors.groupingBy(WeatherResponseDTO::getCategory)); // category로 그룹화

        top3MinTimes = groupedByCategory.values().stream()
                .flatMap(group -> group.stream()
                        .sorted((o1, o2) -> {
                            LocalDate date1 = LocalDate.parse(o1.getFcstDate(), dateFormatter);
                            LocalDate date2 = LocalDate.parse(o2.getFcstDate(), dateFormatter);
                            if (date1.equals(date2)) {
                                return LocalTime.parse(o1.getFcstTime(), timeFormatter).compareTo(LocalTime.parse(o2.getFcstTime(), timeFormatter));
                            } else {
                                return LocalDate.parse(o1.getFcstDate(), dateFormatter).compareTo(LocalDate.parse(o2.getFcstDate(), dateFormatter));
                            }

                        })
                        // fcstTime을 LocalTime으로 변환 후 비교
                        .limit(3)) // 상위 3개만 선택
                .collect(Collectors.toList());


        return new ResponseEntity<>(top3MinTimes, HttpStatus.OK);
    }

}
