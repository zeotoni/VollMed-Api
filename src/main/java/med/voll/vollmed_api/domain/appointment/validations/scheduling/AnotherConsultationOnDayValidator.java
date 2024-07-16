package med.voll.vollmed_api.domain.appointment.validations.scheduling;

import med.voll.vollmed_api.domain.appointment.AppointmentRepository;
import med.voll.vollmed_api.domain.appointment.AppointmentSchedulingData;
import med.voll.vollmed_api.infra.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AnotherConsultationOnDayValidator implements AppointmentScheduleValidator{

    private final AppointmentRepository repository;

    public AnotherConsultationOnDayValidator(AppointmentRepository repository) {
        this.repository = repository;
    }

    public void validate(AppointmentSchedulingData data) {
        LocalDateTime firstTime = data.date().withHour(7);
        LocalDateTime lastTime = data.date().withHour(18);
        boolean hasAnotherSchedule = repository.existsByPatientIdAndDateBetween(data.idPatient(), firstTime, lastTime);

        if (hasAnotherSchedule) {
            throw new EntityNotFoundException("The patient already has an appointment scheduled that day");
        }
    }
}
