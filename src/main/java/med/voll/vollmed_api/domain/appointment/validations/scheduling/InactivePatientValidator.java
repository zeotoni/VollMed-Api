package med.voll.vollmed_api.domain.appointment.validations.scheduling;

import med.voll.vollmed_api.domain.appointment.AppointmentSchedulingData;
import med.voll.vollmed_api.domain.patient.PatientRepository;
import med.voll.vollmed_api.infra.exception.EntityNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class InactivePatientValidator implements AppointmentScheduleValidator{

    private final PatientRepository repository;

    public InactivePatientValidator(PatientRepository repository) {
        this.repository = repository;
    }

    public void validate(AppointmentSchedulingData data) {
        boolean activePatient = repository.findActiveById(data.idPatient());

        if (!activePatient) {
            throw new EntityNotFoundException("The appointment cannot be scheduled with the patient inactive");
        }
    }
}
