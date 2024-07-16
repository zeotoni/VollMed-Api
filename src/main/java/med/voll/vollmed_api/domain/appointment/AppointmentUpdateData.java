package med.voll.vollmed_api.domain.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentUpdateData(

        @NotNull
        Long id,

        Long idDoctor,

        Long idPatient,

        @Future
        LocalDateTime date
) {
}