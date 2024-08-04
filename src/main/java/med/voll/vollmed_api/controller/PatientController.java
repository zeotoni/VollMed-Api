package med.voll.vollmed_api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.vollmed_api.domain.patient.Patient;
import med.voll.vollmed_api.domain.patient.PatientDetailData;
import med.voll.vollmed_api.domain.patient.PatientRegistrationData;
import med.voll.vollmed_api.domain.patient.PatientService;
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

@RestController
@RequestMapping("patients")
@SecurityRequirement(name = "bearer-key")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @PostMapping
    public ResponseEntity<PatientDetailData> registerPatient(@RequestBody @Valid PatientRegistrationData data, UriComponentsBuilder uriBuilder) {
        Patient patient = patientService.registerPatient(data);
        return ResponseEntity.created(patientService.getPatientUri(patient.getId(), uriBuilder)).body(new PatientDetailData(patient));
    }

    @GetMapping
    public ResponseEntity<Page<Patient>> listPatients(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        Page<Patient> page = patientService.listPatients(pagination);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDetailData> getPatientById(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatientById(id));
    }

    @PutMapping
    public ResponseEntity<PatientDetailData> updatePatient(@RequestBody @Valid PatientUpdateData data) {
        return ResponseEntity.ok(patientService.updatePatient(data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
