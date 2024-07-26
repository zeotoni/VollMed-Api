package med.voll.vollmed_api.domain.doctor;

import med.voll.vollmed_api.domain.address.AddressData;
import med.voll.vollmed_api.domain.appointment.Appointment;
import med.voll.vollmed_api.domain.patient.Patient;
import med.voll.vollmed_api.domain.patient.PatientRegistrationData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class DoctorRepositoryTest {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private TestEntityManager em;

    @Test
    @DisplayName("Should return null when the only registered doctor is not available on the date")
    void chooseFreeRandomDoctorCase1() {
        var nextMonday = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = registerDoctor("Medico", "medico@voll.med", "123456", "3398567124", Specialty.CARDIOLOGY);
        var paciente = registerPatient("Paciente", "paciente@email.com", "00000000000", "5598741255");
        scheduleAppointment(doctor, paciente, nextMonday);

        var availableDoctor =  doctorRepository.chooseAvailableRandomDoctor(Specialty.CARDIOLOGY, nextMonday);
        assertThat(availableDoctor).isNull();
    }

    @Test
    @DisplayName("Should return Doctor when he is available on the date")
    void chooseFreeRandomDoctorCase2() {
        var nextMonday = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atTime(10, 0);

        var doctor = registerDoctor("Medico", "medico@voll.med", "123456", "3398567124", Specialty.CARDIOLOGY);

        var availableDoctor =  doctorRepository.chooseAvailableRandomDoctor(Specialty.CARDIOLOGY, nextMonday);
        assertThat(availableDoctor).isEqualTo(doctor);
    }

    private void scheduleAppointment(Doctor doctor, Patient patient, LocalDateTime date) {
        em.persist(new Appointment(null, doctor, patient, date, null));
    }

    private Doctor registerDoctor(String name, String email, String crm, String phone, Specialty specialty) {
        var doctor = new Doctor(doctorData(name, email, crm, phone, specialty));
        em.persist(doctor);
        return doctor;
    }

    private Patient registerPatient(String name, String email, String cpf, String phone) {
        var patient = new Patient(patientData(name, email, cpf, phone));
        em.persist(patient);
        return patient;
    }

    private DoctorRegistrationData doctorData(String name, String email, String crm, String phone, Specialty specialty) {
        return new DoctorRegistrationData(
                name,
                email,
                crm,
                phone,
                specialty,
                addressData()
        );
    }

    private PatientRegistrationData patientData(String nome, String email, String cpf, String phone) {
        return new PatientRegistrationData(
                nome,
                email,
                cpf,
                phone,
                addressData()
        );
    }

    private AddressData addressData() {
        return new AddressData(
                "street xpto",
                "00000000",
                "Brasilia",
                "DF",
                "null",
                null
        );
    }
}