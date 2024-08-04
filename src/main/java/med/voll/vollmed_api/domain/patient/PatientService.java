package med.voll.vollmed_api.domain.patient;

import jakarta.transaction.Transactional;
import med.voll.vollmed_api.domain.address.AddressService;
import med.voll.vollmed_api.infra.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class PatientService {

    private static final String PATIENT_NOT_FOUND_MESSAGE = "Patient id not found or inactive";

    private final PatientRepository repository;
    private final AddressService addressService;

    public PatientService(PatientRepository repository, AddressService addressService) {
        this.repository = repository;
        this.addressService = addressService;
    }

    @Transactional
    public Patient registerPatient(PatientRegistrationData data) {
        Patient patient = new Patient(data);
        return repository.save(patient);
    }

    public URI getPatientUri(Long doctorId, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("/doctors/{id}").buildAndExpand(doctorId).toUri();
    }

    public Page<Patient> listPatients(Pageable pagination) {
        return repository.findAllByActiveTrue(pagination);
    }

    public PatientDetailData getPatientById(Long id) {
        Patient patient = repository.findByIdAndActive(id)
                .orElseThrow(() -> new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE));
        return new PatientDetailData(patient);
    }

    @Transactional
    public PatientDetailData updatePatient(PatientUpdateData data) {
        Patient patient = repository.findById(data.id())
                .orElseThrow(() -> new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE));

        if (data.name() != null) {
            patient.setName(data.name());
        }

        if (data.phone() != null) {
            patient.setPhone(data.phone());
        }

        if (data.address() != null) {
            addressService.updateAddress(patient.getAddress(), data.address());
        }

        return new PatientDetailData(patient);
    }

    @Transactional
    public void deletePatient(Long id) {
        Patient patient = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(PATIENT_NOT_FOUND_MESSAGE));

        patient.setActive(false);
    }
}
