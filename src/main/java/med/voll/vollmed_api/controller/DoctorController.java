package med.voll.vollmed_api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.vollmed_api.domain.doctor.Doctor;
import med.voll.vollmed_api.domain.doctor.DoctorDetailData;
import med.voll.vollmed_api.domain.doctor.DoctorRegistrationData;
import med.voll.vollmed_api.domain.doctor.DoctorRepository;
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

import java.net.URI;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    private final DoctorRepository repository;

    public DoctorController(DoctorRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DoctorDetailData> register(@RequestBody @Valid DoctorRegistrationData data, UriComponentsBuilder uriBuilder) {
        Doctor doctor = new Doctor(data);
        repository.save(doctor);

        URI uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(new DoctorDetailData(doctor));
    }

    @GetMapping
    public ResponseEntity<Page<Doctor>> listWithPagination(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        var page = repository.findAllByActiveTrue(pagination);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<DoctorDetailData> update(@RequestBody @Valid DoctorUpdateData data) {
        Doctor doctor = repository.getReferenceById(data.id());
        doctor.updateData(data);

        return ResponseEntity.ok(new DoctorDetailData(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Doctor doctor = repository.getReferenceById(id);
        doctor.delete();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDetailData> detail(@PathVariable Long id) {
        Doctor doctor = repository.getReferenceById(id);
        return ResponseEntity.ok(new DoctorDetailData(doctor));
    }
}
