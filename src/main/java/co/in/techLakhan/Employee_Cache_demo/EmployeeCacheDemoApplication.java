package co.in.techLakhan.Employee_Cache_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class EmployeeCacheDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeCacheDemoApplication.class, args);
	}

}
