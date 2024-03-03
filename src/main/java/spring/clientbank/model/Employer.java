package spring.clientbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Employer extends AbstractEntity{
    @Column(name = "employer_name", nullable = false, length = 50)
    private String name;
    @Column(nullable = false)
    private String address;
    @ManyToMany(mappedBy = "employers", fetch = FetchType.EAGER)
    private List<Customer> customers;
}
