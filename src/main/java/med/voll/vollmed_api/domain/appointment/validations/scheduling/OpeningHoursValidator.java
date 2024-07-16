package med.voll.vollmed_api.domain.appointment.validations.scheduling;

import med.voll.vollmed_api.domain.appointment.AppointmentSchedulingData;
import med.voll.vollmed_api.infra.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Component
public class OpeningHoursValidator implements AppointmentScheduleValidator{

    public void validate(AppointmentSchedulingData data) {

        LocalDateTime appointmentDate = data.date();

        boolean isSunday = appointmentDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isBeforeOpening = appointmentDate.getHour() < 7;
        boolean isAfterOpening = appointmentDate.getHour() > 18;

        if (isSunday || isBeforeOpening || isAfterOpening) {
            throw new EntityNotFoundException("Appointment outside opening hours");
        }
    }
}
