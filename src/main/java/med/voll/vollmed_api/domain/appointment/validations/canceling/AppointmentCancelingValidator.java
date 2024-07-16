package med.voll.vollmed_api.domain.appointment.validations.canceling;

import med.voll.vollmed_api.domain.appointment.AppointmentCancelingData;

public interface AppointmentCancelingValidator {
    void validate(AppointmentCancelingData data);
}