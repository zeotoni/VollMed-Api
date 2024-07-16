package med.voll.vollmed_api.domain.appointment;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import med.voll.vollmed_api.domain.doctor.Specialty;

import java.time.LocalDateTime;

public record AppointmentSchedulingData(

        Long idDoctor,

        @NotNull
        Long idPatient,

        @NotNull
        @Future
        LocalDateTime date,

        Specialty specialty
) {
}
