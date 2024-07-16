package med.voll.vollmed_api.domain.appointment.validations.scheduling;

import med.voll.vollmed_api.domain.appointment.AppointmentSchedulingData;

public interface AppointmentScheduleValidator {

    void validate(AppointmentSchedulingData data);
}
