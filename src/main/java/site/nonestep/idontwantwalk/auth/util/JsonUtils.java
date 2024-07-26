package site.nonestep.idontwantwalk.auth.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public class JsonUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void writeJsonExceptionResponse(HttpServletResponse response, HttpStatus httpStatus, String msg)
            throws IOException {

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

//        ErrorResponse baseResponseDto = new ErrorResponse(httpStatus, msg);

        //json으로 변환하여 response에 저장
//        String stringResponseData = objectMapper.writeValueAsString(baseResponseDto);
        response.getWriter().write(msg);
    }
}
