package med.voll.vollmed_api.domain.appointment.validations.scheduling;

import med.voll.vollmed_api.domain.appointment.AppointmentSchedulingData;
import med.voll.vollmed_api.infra.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component("advanceTimeValidatorSchedule")
public class AdvanceTimeValidator implements AppointmentScheduleValidator{

    public void validate(AppointmentSchedulingData data){

        LocalDateTime appointmentDate = data.date();
        LocalDateTime currentTime = LocalDateTime.now();
        long differenceInMinutes = Duration.between(currentTime, appointmentDate).toMinutes();

        if (differenceInMinutes < 30){
            throw new EntityNotFoundException("Appointment must be scheduled at least 30 minutes in advance");
        }
    }
}
