package spring.clientbank.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Customer extends AbstractEntity {
    @Column(name = "customer_name", nullable = false, length = 50)
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(unique = true, nullable = false, length = 50)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(name = "age", nullable = false)
    private Integer age;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Account> accounts;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "customer_employer",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "employer_id")
    )
    private List<Employer> employers;


    public Customer(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }
}
