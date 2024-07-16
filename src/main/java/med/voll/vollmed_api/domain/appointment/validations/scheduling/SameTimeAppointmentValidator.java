package med.voll.vollmed_api.domain.appointment.validations.scheduling;

import med.voll.vollmed_api.domain.appointment.AppointmentRepository;
import med.voll.vollmed_api.domain.appointment.AppointmentSchedulingData;
import med.voll.vollmed_api.infra.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class SameTimeAppointmentValidator implements AppointmentScheduleValidator{

    private final AppointmentRepository repository;

    public SameTimeAppointmentValidator(AppointmentRepository repository) {
        this.repository = repository;
    }

    public void validate(AppointmentSchedulingData data) {
      boolean hasAppoinment = repository.existsByDoctorIdAndDate(data.idDoctor(), data.date());

        if (hasAppoinment) {
            throw new EntityNotFoundException("The doctor already has an appointment scheduled at this time");
        }
    }
}