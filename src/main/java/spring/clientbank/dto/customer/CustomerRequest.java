package spring.clientbank.dto.customer;

import javax.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {
    @NotBlank(message = "The name cannot be blank")
    @Size(min = 2, message = "The name must consist of at least 2 characters")
    private String name;
    @Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z]).{8,}", message = "The password must be at least 8 characters long " +
            "and contain at least 1 uppercase and 1 lowercase letter")
    private String password;
    @Pattern(regexp = "(^$|^(\\+38)?0[0-9]{9}$)",  message = "Invalid number format")
    private String phoneNumber;
    @Email(message = "Invalid email format")
    private String email;
    @Min(value = 18, message = "The age must be at least 18 years old")
    @NotNull
    private Integer age;
}
