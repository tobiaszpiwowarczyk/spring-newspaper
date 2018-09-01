package pl.toby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class InfoServiceApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(InfoServiceApiApplication.class, args);
	}
}
