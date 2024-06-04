package med.voll.vollmed_api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.vollmed_api.doctor.DataRegisterDoctors;
import med.voll.vollmed_api.doctor.Doctor;
import med.voll.vollmed_api.doctor.DoctorRepository;
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
    public void register(@RequestBody @Valid DataRegisterDoctors data) {
        repository.save(new Doctor(data));
    }
}
