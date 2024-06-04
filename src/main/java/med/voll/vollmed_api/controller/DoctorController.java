package med.voll.vollmed_api.controller;

import med.voll.vollmed_api.doctor.DataRegisterDoctors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("doctors")
public class DoctorController {

    @PostMapping
    public void register(@RequestBody DataRegisterDoctors data) {
        System.out.println(data);
    }
}
