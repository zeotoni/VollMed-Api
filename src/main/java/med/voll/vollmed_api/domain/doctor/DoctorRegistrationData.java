package med.voll.vollmed_api.domain.doctor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import med.voll.vollmed_api.domain.address.AddressData;

public record DoctorRegistrationData(

        @NotBlank(message = "Name cannot be blank")
        @Size(min = 3, message = "Name must have at least 3 characters")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email(message = "Invalid email format")
        String email,

        @NotBlank(message = "CRM cannot be blank")
        @Pattern(regexp = "\\d{4,6}", message = "Invalid CRM format. Must contain 4 to 6 digits")
        String crm,

        @NotBlank(message = "Phone cannot be blank")
        String phone,

        @NotNull(message = "Specialty cannot be blank")
        Specialty specialty,

        @NotNull(message = "Address cannot be blank")
        @Valid
        AddressData address) {
}
