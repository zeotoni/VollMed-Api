package med.voll.vollmed_api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.vollmed_api.doctor.DoctorRegistrationData;
import med.voll.vollmed_api.doctor.Doctor;
import med.voll.vollmed_api.doctor.DoctorListingData;
import med.voll.vollmed_api.doctor.DoctorRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    private DoctorRepository repository;

    public DoctorController(DoctorRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    @Transactional
    public void register(@RequestBody @Valid DoctorRegistrationData data) {
        repository.save(new Doctor(data));
    }

    @GetMapping
    public Page<DoctorListingData> list(@PageableDefault(size = 10, sort = {"name"}) Pageable pagination) {
        return repository.findAll(pagination).map(DoctorListingData::new);
    }
}
