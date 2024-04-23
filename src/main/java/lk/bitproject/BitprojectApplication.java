package lk.bitproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BitprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitprojectApplication.class, args);

		System.out.println("=============================================");
		System.out.println("======== Project Started Successfully =======");
		System.out.println("=============================================");

	}

}
