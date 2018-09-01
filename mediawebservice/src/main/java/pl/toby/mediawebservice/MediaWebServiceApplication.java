package pl.toby.mediawebservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class MediaWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MediaWebServiceApplication.class, args);
	}
}
