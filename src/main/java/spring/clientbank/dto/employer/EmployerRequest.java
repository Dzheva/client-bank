package spring.clientbank.dto.employer;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployerRequest {
    @NotBlank(message = "Employer name cannot be blank")
    @Size(min = 3, message = "Employer name must be at least 3 characters")
    private String name;
    @NotBlank(message = "Address cannot be blank")
    @Size(min = 3, message = "Address must be at least 3 characters")
    private String address;
}
