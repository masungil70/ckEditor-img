package kr.or.kosa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class CfEditorImgApplication {

	public static void main(String[] args) {
		SpringApplication.run(CfEditorImgApplication.class, args);
	}

}
