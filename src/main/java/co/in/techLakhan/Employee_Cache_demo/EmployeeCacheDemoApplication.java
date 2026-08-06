package co.in.techLakhan.Employee_Cache_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableCaching
@EnableAsync
public class EmployeeCacheDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeCacheDemoApplication.class, args);
	}
}
