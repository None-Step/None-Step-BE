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

        // UTF+8로 설정한다.
        ZoneId zoneId = ZoneId.of("Asia/Seoul");
        LocalDateTime localDateTime = LocalDateTime.now();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);

        // 사용자가 호출하는 시간이 18시 30분 이라면 18시부터 날씨를 보여주기 위해 한 시간을 빼준다.
        zdt = zdt.minusHours(1);

        String baseTime = zdt.format(zoneTimeFormat);
        String baseDate = zdt.format(zoneDateFormat);


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
        log.info("url : {}",url);

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

}
