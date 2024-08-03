package med.voll.vollmed_api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.vollmed_api.domain.doctor.Doctor;
import med.voll.vollmed_api.domain.doctor.DoctorDetailData;
import med.voll.vollmed_api.domain.doctor.DoctorRegistrationData;
import med.voll.vollmed_api.domain.doctor.DoctorService;
import med.voll.vollmed_api.domain.doctor.DoctorUpdateData;
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
@RequestMapping("doctors")
@SecurityRequirement(name = "bearer-key")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping
    public ResponseEntity<DoctorDetailData> registerDoctor(@RequestBody @Valid DoctorRegistrationData data, UriComponentsBuilder uriBuilder) {
        Doctor doctor = doctorService.registerDoctor(data);
        return ResponseEntity.created(doctorService.getDoctorUri(doctor.getId(), uriBuilder)).body(new DoctorDetailData(doctor));
    }

    @GetMapping
    public ResponseEntity<Page<Doctor>> listDoctors(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        Page<Doctor> page = doctorService.listDoctors(pagination);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    public ResponseEntity<DoctorDetailData> updateDoctor(@RequestBody @Valid DoctorUpdateData data) {
        return ResponseEntity.ok(doctorService.updateDoctor(data));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailData> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }
}
