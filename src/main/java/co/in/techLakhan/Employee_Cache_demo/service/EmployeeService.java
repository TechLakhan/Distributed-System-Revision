package co.in.techLakhan.Employee_Cache_demo.service;

import co.in.techLakhan.Employee_Cache_demo.entity.Employee;
import co.in.techLakhan.Employee_Cache_demo.entity.KafkaTest;
import co.in.techLakhan.Employee_Cache_demo.exception.EmployeeNotFoundException;
import co.in.techLakhan.Employee_Cache_demo.repository.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import static co.in.techLakhan.Employee_Cache_demo.constants.ErrorCodes.EXP001;

@Service
public class EmployeeService {


    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);

    private final EmployeeRepository employeeRepository;

    private final EmailService emailService;

    private final KafkaTest kafkaTest;

    public EmployeeService(EmployeeRepository employeeRepository, EmailService emailService, KafkaTest kafkaTest) {
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
        this.kafkaTest = kafkaTest;
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
        logger.info("EmployeeService Thread : {}", Thread.currentThread().getName());
        kafkaTest.sentMail(employeeId);
    }

    public String createNewEmployeeRecord(Employee employee) {
        logger.info("In createNewEmployeeRecord()");
        try {
            Employee newEmployee = new Employee();
            newEmployee.setId(employee.getId());
            newEmployee.setName(employee.getName());
            newEmployee.setDepartment(employee.getDepartment());
            newEmployee.setSalary(employee.getSalary());
            employeeRepository.save(newEmployee);
            return "new employee record created";
        } catch (RuntimeException exception) {
            logger.error("Error creating employee record with username {}", employee.getName());
            throw new RuntimeException("Error creating new employee record");
        }
    }
}
