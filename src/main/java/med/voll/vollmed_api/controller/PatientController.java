package med.voll.vollmed_api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.vollmed_api.domain.patient.Patient;
import med.voll.vollmed_api.domain.patient.PatientDetailData;
import med.voll.vollmed_api.domain.patient.PatientRegistrationData;
import med.voll.vollmed_api.domain.patient.PatientRepository;
import med.voll.vollmed_api.domain.patient.PatientUpdateData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("patients")
public class PatientController {

    private final PatientRepository repository;

    public PatientController(PatientRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<PatientDetailData> register(@RequestBody @Valid PatientRegistrationData data, UriComponentsBuilder uriBuilder) {
        Patient patient = new Patient(data);
        repository.save(patient);

        URI uri = uriBuilder.path("/patients/{id}").buildAndExpand(patient.getId()).toUri();

        return ResponseEntity.created(uri).body(new PatientDetailData(patient));
    }

    @GetMapping
    public ResponseEntity<Page<Patient>> listingWithPagination(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        Page<Patient> page = repository.findAllByActiveTrue(pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDetailData> detail(@PathVariable Long id) {
        Patient patient = repository.getReferenceById(id);
        return ResponseEntity.ok(new PatientDetailData(patient));
    }

    @PutMapping
    @Transactional
    public ResponseEntity<PatientDetailData> update(@RequestBody @Valid PatientUpdateData data) {
        Patient patient = repository.getReferenceById(data.id());
        patient.updateData(data);

        return ResponseEntity.ok(new PatientDetailData(patient));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Patient patient = repository.getReferenceById(id);
        patient.delete();

        return ResponseEntity.noContent().build();
    }
}
