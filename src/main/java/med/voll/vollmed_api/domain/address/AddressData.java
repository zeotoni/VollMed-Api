package med.voll.vollmed_api.domain.address;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressData(

        @NotBlank(message = "Street cannot be blank")
        String street,

        @NotBlank(message = "Postal code cannot be blank")
        @Pattern(regexp = "\\d{8}", message = "Invalid postal code format. Must contain 8 digits")
        String postalCode,

        @NotBlank(message = "City cannot be blank")
        String city,

        @NotBlank(message = "State cannot be blank")
        String state,

        String complement,

        String number) {
}