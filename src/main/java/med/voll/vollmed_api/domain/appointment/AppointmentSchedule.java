package med.voll.vollmed_api.domain.appointment;

import med.voll.vollmed_api.domain.appointment.validations.canceling.AppointmentCancelingValidator;
import med.voll.vollmed_api.domain.appointment.validations.scheduling.AppointmentScheduleValidator;
import med.voll.vollmed_api.domain.doctor.Doctor;
import med.voll.vollmed_api.domain.doctor.DoctorRepository;
import med.voll.vollmed_api.domain.patient.Patient;
import med.voll.vollmed_api.domain.patient.PatientRepository;
import med.voll.vollmed_api.infra.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AppointmentSchedule {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final List<AppointmentScheduleValidator> validators;
    private final List<AppointmentCancelingValidator> cancelingValidators;

    public AppointmentSchedule(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository, List<AppointmentScheduleValidator> validators, List<AppointmentCancelingValidator> cancelingValidators) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.validators = validators;
        this.cancelingValidators = cancelingValidators;
    }

    public AppointmentDetailData scheduleAppointment(AppointmentSchedulingData data) {

        if(data.idDoctor() != null && !doctorRepository.existsById(data.idDoctor())) {
            throw new EntityNotFoundException("Doctor ID does not exist");
        }

        if(!patientRepository.existsById(data.idPatient())) {
            throw new EntityNotFoundException("Patient ID does not exist");
        }

        validators.forEach(v -> v.validate(data));

        Doctor doctor = chooseDoctor(data);

        if (doctor == null) {
            throw new EntityNotFoundException("There are no doctors available on this date");
        }

        Patient patient = patientRepository.getReferenceById(data.idPatient());
        Appointment appointment = new Appointment(null, doctor, patient, data.date(), null);

        appointmentRepository.save(appointment);

        return new AppointmentDetailData(appointment);
    }

    public void deleteAppointment(AppointmentCancelingData data) {

        if (!appointmentRepository.existsById(data.id())) {
            throw new EntityNotFoundException("Appointment not found with ID: " + data.id());
        }

        cancelingValidators.forEach(v -> v.validate(data));
        Appointment appointment = appointmentRepository.getReferenceById(data.id());
        appointmentRepository.delete(appointment);
    }

    public AppointmentDetailData updateAppointment(AppointmentUpdateData data) {
        Appointment appointment = appointmentRepository.findById(data.id())
                .orElseThrow(() -> new EntityNotFoundException("Appointment id not found"));

        if (data.idDoctor() != null) {
            Doctor doctor = doctorRepository.findById(data.idDoctor())
                    .orElseThrow(() -> new EntityNotFoundException("Doctor id not found"));
            appointment.setDoctor(doctor);
        }

        if (data.idPatient() != null) {
            Patient patient = patientRepository.findById(data.idPatient())
                    .orElseThrow(() -> new EntityNotFoundException("Patient id not found"));
            appointment.setPatient(patient);
        }

        if (data.date() != null) {
            appointment.setDate(data.date());
        }

        return new AppointmentDetailData(appointment);
    }

    public Page<AppointmentDetailData> listAppointments(Pageable pagination) {
        Page<Appointment> page = appointmentRepository.findAll(pagination);
        return page.map(AppointmentDetailData::new);
    }

    public AppointmentDetailData getAppointmentById(Long id) {
        Appointment appointment = appointmentRepository.getReferenceById(id);
        return new AppointmentDetailData(appointment);
    }

    private Doctor chooseDoctor(AppointmentSchedulingData data) {
        if (data.idDoctor() != null) {
            return doctorRepository.getReferenceById(data.idDoctor());
        }

        if (data.specialty() == null) {
            throw new EntityNotFoundException("Specialty is mandatory when the doctor is not chosen");
        }

        return doctorRepository.chooseFreeRandomDoctor(data.specialty(), data.date());
    }
}
