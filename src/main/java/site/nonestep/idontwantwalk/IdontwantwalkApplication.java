package site.nonestep.idontwantwalk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class IdontwantwalkApplication {

	public static void main(String[] args) {

		SpringApplication.run(IdontwantwalkApplication.class, args);
		log.info("Application start");
	}

}
