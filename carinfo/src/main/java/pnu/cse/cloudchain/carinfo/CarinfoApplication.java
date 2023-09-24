package pnu.cse.cloudchain.carinfo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class CarinfoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarinfoApplication.class, args);
	}

}
