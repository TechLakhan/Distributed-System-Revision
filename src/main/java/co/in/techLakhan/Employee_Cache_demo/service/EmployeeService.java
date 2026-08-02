package co.in.techLakhan.Employee_Cache_demo.service;

import co.in.techLakhan.Employee_Cache_demo.entity.Employee;
import co.in.techLakhan.Employee_Cache_demo.exception.EmployeeNotFoundException;
import co.in.techLakhan.Employee_Cache_demo.repository.EmployeeRepository;
import co.in.techLakhan.Employee_Cache_demo.util.EmployeeCacheProperties;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static co.in.techLakhan.Employee_Cache_demo.constants.ErrorCodes.EXP001;

@Service
public class EmployeeService {


    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    private final EmployeeCacheProperties cacheProperties;


    public EmployeeService(EmployeeRepository employeeRepository, RedisTemplate<String, Object> redisTemplate, EmployeeCacheProperties cacheProperties) {
        this.employeeRepository = employeeRepository;
        this.redisTemplate = redisTemplate;
        this.cacheProperties = cacheProperties;
    }
    public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
        String key = getEmployeeCacheKey(id);
        Employee cachedEmployee = (Employee) redisTemplate.opsForValue().get(key);
        if (cachedEmployee != null) {
            logger.info("Cache hit for key {}", key);
            return cachedEmployee;
        } else {
            logger.info("Cache miss for key {}", key);
            Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(404, EXP001.message(), HttpStatus.NOT_FOUND));
            redisTemplate.opsForValue().set(key, employee, cacheProperties.getTtl(), cacheProperties.getUnit());
            return employee;
        }
    }

    private String getEmployeeCacheKey(Long id){
        return "Employee:" + id;
    }

    public void updateEmployeeDetails(Employee newDetails) throws EmployeeNotFoundException {
        Long employeeId = newDetails.getId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(404, EXP001.message(), HttpStatus.NOT_FOUND)));
        employee.setId(employeeId);
        employee.setName(newDetails.getName());
        employee.setDepartment(newDetails.getDepartment());
        employee.setSalary(newDetails.getSalary());
        employeeRepository.save(employee);
        redisTemplate.delete(getEmployeeCacheKey(employeeId));
    }
}
