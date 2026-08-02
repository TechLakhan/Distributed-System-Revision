package co.in.techLakhan.Employee_Cache_demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "employee ")
public class Employee {

    @Id
    @NotNull
    private Long id;
    private String name;
    private String department;
    private Double salary;
}
