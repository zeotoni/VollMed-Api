package med.voll.vollmed_api.domain.appointment.validations.scheduling;

import med.voll.vollmed_api.domain.appointment.AppointmentSchedulingData;
import med.voll.vollmed_api.domain.doctor.DoctorRepository;
import med.voll.vollmed_api.infra.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class InactiveDoctorValidator implements AppointmentScheduleValidator{

    private final DoctorRepository repository;

    public InactiveDoctorValidator(DoctorRepository repository) {
        this.repository = repository;
    }

    public void validate(AppointmentSchedulingData data) {

        if (data.idDoctor() == null) {
            return;
        }

        boolean activeDoctor = repository.findActiveById(data.idDoctor());

        if (!activeDoctor) {
            throw new EntityNotFoundException("The appointment cannot be scheduled with the doctor inactive");
        }
    }
}
