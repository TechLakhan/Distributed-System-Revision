package co.in.techLakhan.Employee_Cache_demo.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@ConfigurationProperties(prefix = "employee.cache")
@Component
@Data
public class EmployeeCacheProperties {

    private Long ttl;
    private TimeUnit unit;
    private String prefix;
    private boolean enabled;


}
