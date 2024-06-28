package med.voll.vollmed_api.domain.patient;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import med.voll.vollmed_api.domain.address.AddressData;

public record PatientRegistrationData(

        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, message = "Name must have at least 3 characters")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "CPF cannot be blank")
        @Pattern(regexp = "\\d{11}", message = "Invalid CPF format. Must contain exactly 11 digits")
        String cpf,

        @NotBlank(message = "Phone cannot be blank")
        String phone,

        @NotNull(message = "Address cannot be blank")
        @Valid
        AddressData address
) {
}