package med.voll.vollmed_api.domain.appointment;

import jakarta.validation.constraints.NotNull;

public record AppointmentCancelingData(

        @NotNull
        Long id,

        @NotNull
        ReasonCancelation reasonCancelation) {
}