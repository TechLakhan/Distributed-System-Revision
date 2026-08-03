package co.in.techLakhan.Employee_Cache_demo.service;

import co.in.techLakhan.Employee_Cache_demo.entity.Employee;
import co.in.techLakhan.Employee_Cache_demo.exception.EmployeeNotFoundException;
import co.in.techLakhan.Employee_Cache_demo.repository.EmployeeRepository;
import co.in.techLakhan.Employee_Cache_demo.util.EmployeeCacheProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
    @Cacheable(value = "employees", key = "#id")
    public Employee getEmployeeById(Long id) throws EmployeeNotFoundException {
        logger.info("Fetching employee {} from database", id);
        Employee employee = employeeRepository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(404, EXP001.message(), HttpStatus.NOT_FOUND));
        return employee;
    }

    @CacheEvict(value = "employees", key = "#newDetails.id")
    public void updateEmployeeDetails(Employee newDetails) throws EmployeeNotFoundException {
        Long employeeId = newDetails.getId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(404, EXP001.message(), HttpStatus.NOT_FOUND));
        employee.setId(employeeId);
        employee.setName(newDetails.getName());
        employee.setDepartment(newDetails.getDepartment());
        employee.setSalary(newDetails.getSalary());
        employeeRepository.save(employee);
    }
}
