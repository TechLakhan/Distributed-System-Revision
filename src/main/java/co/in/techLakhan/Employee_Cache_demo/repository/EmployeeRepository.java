package co.in.techLakhan.Employee_Cache_demo.repository;

import co.in.techLakhan.Employee_Cache_demo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Override
    Optional<Employee> findById(Long id);
}
