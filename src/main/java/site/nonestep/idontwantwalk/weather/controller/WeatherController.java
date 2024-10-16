package site.nonestep.idontwantwalk.weather.controller;

import com.nimbusds.jose.shaded.gson.*;
import com.nimbusds.jose.shaded.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import site.nonestep.idontwantwalk.config.AuthConfig;
import site.nonestep.idontwantwalk.road.service.RoadService;
import site.nonestep.idontwantwalk.subway.service.SubwayService;
import site.nonestep.idontwantwalk.weather.dto.WeatherRequestDTO;
import site.nonestep.idontwantwalk.weather.dto.WeatherResponseDTO;

import java.lang.reflect.Type;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
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
        String url = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?";
        url += "serviceKey=" + authConfig.getWeather();
        url += "&pageNo=1&numOfRows=1000&dataType=JSON";
        url += "&base_date=" + weatherRequestDTO.getBaseDate();
        url += "&base_time=" + weatherRequestDTO.getBaseTime();
        url += "&nx=" + weatherRequestDTO.getX();
        url += "&ny=" + weatherRequestDTO.getY();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        // 기상청 api 호출
        Response response = client.newCall(request).execute();

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
        Type personListType = new TypeToken<List<WeatherResponseDTO>>(){}.getType();
        List<WeatherResponseDTO> result = gson.fromJson(jsonArray,personListType);
        result = result.stream().filter(this::isCheckWeatherCode).collect(Collectors.toList());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HHmm");
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");


        Map<String, List<WeatherResponseDTO>> groupedByCategory = result.stream()
                .collect(Collectors.groupingBy(WeatherResponseDTO::getCategory)); // category로 그룹화

        List<WeatherResponseDTO> top3MinTimes = groupedByCategory.values().stream()
                .flatMap(group -> group.stream()
                        .sorted((o1, o2) -> {
                            LocalDate date1 = LocalDate.parse(o1.getFcstDate(), dateFormatter);
                            LocalDate date2 = LocalDate.parse(o2.getFcstDate(), dateFormatter);
                            if(date1.equals(date2)){
                                return LocalTime.parse(o1.getFcstTime(), timeFormatter).compareTo(LocalTime.parse(o2.getFcstTime(), timeFormatter));
                            }else{
                                return LocalDate.parse(o1.getFcstDate(), dateFormatter).compareTo(LocalDate.parse(o2.getFcstDate(), dateFormatter));
                            }

                        }) // fcstTime을 LocalTime으로 변환 후 비교
                        .limit(3)) // 상위 3개만 선택
                .collect(Collectors.toList());


        return new ResponseEntity<>(top3MinTimes, HttpStatus.OK);
    }

    // filter는 무조건 T/F값을 return 하기 때문에 boolean 함수를 새로 만든다.
    // 그 후, 필요한 코드값만 switch문을 통해 받아온다. 나머지는 false값을 줘서 걸러준다.
    public boolean isCheckWeatherCode(WeatherResponseDTO weatherResponseDTO){
        switch (weatherResponseDTO.getCategory()){
            case "T1H" :
            case "RN1" :
            case "SKY" :
            case "PTY" :
            case "LGT" :
            case "WSD" :
                return true;
            default:
                return false;
        }
    }

}
