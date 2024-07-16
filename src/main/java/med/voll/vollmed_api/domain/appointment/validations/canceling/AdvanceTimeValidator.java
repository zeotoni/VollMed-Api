package med.voll.vollmed_api.domain.appointment.validations.canceling;

import med.voll.vollmed_api.domain.appointment.Appointment;
import med.voll.vollmed_api.domain.appointment.AppointmentCancelingData;
import med.voll.vollmed_api.domain.appointment.AppointmentRepository;
import med.voll.vollmed_api.infra.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("advanceTimeValidatorCanceling")
public class AdvanceTimeValidator implements AppointmentCancelingValidator{

    private final AppointmentRepository appointmentRepository;

    public AdvanceTimeValidator(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    public void validate(AppointmentCancelingData data) {

        Appointment appointment = appointmentRepository.getReferenceById(data.id());
        LocalDateTime currentTime = LocalDateTime.now();
        long differenceInHours = Duration.between(currentTime, appointment.getDate()).toHours();

        if (differenceInHours < 24){
            throw new EntityNotFoundException("Appointment must be scheduled at least 24 hours in advance!");
        }
    }
}