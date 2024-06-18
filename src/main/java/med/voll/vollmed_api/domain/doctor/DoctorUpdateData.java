package med.voll.vollmed_api.domain.doctor;

import jakarta.validation.constraints.NotNull;
import med.voll.vollmed_api.domain.address.AddressData;

public record DoctorUpdateData(

        @NotNull
        Long id,

        String name,

        String phone,

        AddressData address) {
}
