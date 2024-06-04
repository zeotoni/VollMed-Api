package med.voll.vollmed_api.doctor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.vollmed_api.address.AddressData;

public record DataRegisterDoctors(

        @NotBlank
        String name,

        @NotBlank
        @Email
        String email,

        @NotBlank
        @Pattern(regexp = "\\d{4,6}")
        String crm,

        @NotBlank
        String phone,

        @NotNull
        Specialty specialty,

        @NotNull
        @Valid
        AddressData address) {
}
