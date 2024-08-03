package med.voll.vollmed_api.domain.doctor;

import jakarta.transaction.Transactional;
import med.voll.vollmed_api.domain.address.AddressService;
import med.voll.vollmed_api.infra.exception.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Service
public class DoctorService {

    private static final String DOCTOR_NOT_FOUND_MESSAGE = "Doctor id not found or inactive";

    private final DoctorRepository repository;
    private final AddressService addressService;

    public DoctorService(DoctorRepository repository, AddressService addressService) {
        this.repository = repository;
        this.addressService = addressService;
    }

    @Transactional
    public Doctor registerDoctor(DoctorRegistrationData data) {
        Doctor doctor = new Doctor(data);
        return repository.save(doctor);
    }

    public URI getDoctorUri(Long doctorId, UriComponentsBuilder uriBuilder) {
        return uriBuilder.path("/doctors/{id}").buildAndExpand(doctorId).toUri();
    }

    public Page<Doctor> listDoctors(Pageable pagination) {
        return repository.findAllByActiveTrue(pagination);
    }

    @Transactional
    public DoctorDetailData updateDoctor(DoctorUpdateData data) {
        Doctor doctor = repository.findById(data.id())
                .orElseThrow(() -> new EntityNotFoundException(DOCTOR_NOT_FOUND_MESSAGE));

        if (data.name() != null) {
            doctor.setName(data.name());
        }

        if (data.phone() != null) {
            doctor.setPhone(data.phone());
        }

        if (data.address() != null) {
            addressService.updateAddress(doctor.getAddress(), data.address());
        }

        return new DoctorDetailData(doctor);
    }

    @Transactional
    public void deleteDoctor(Long id) {
        Doctor doctor = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(DOCTOR_NOT_FOUND_MESSAGE));

        doctor.setActive(false);
    }

    public DoctorDetailData getDoctorById(Long id) {
        Doctor doctor = repository.findByIdAndActive(id)
                .orElseThrow(() -> new EntityNotFoundException(DOCTOR_NOT_FOUND_MESSAGE));
        return new DoctorDetailData(doctor);
    }
}
