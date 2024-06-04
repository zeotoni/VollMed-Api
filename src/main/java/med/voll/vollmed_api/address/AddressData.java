package med.voll.vollmed_api.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressData(

        @NotBlank
        String street,

        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String postalCode,

        @NotBlank
        String city,

        @NotBlank
        String state,

        String complement,

        String number) {
}