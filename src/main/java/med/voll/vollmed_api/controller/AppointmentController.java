package med.voll.vollmed_api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.vollmed_api.domain.appointment.AppointmentCancelingData;
import med.voll.vollmed_api.domain.appointment.AppointmentDetailData;
import med.voll.vollmed_api.domain.appointment.AppointmentSchedule;
import med.voll.vollmed_api.domain.appointment.AppointmentSchedulingData;
import med.voll.vollmed_api.domain.appointment.AppointmentUpdateData;
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

@RestController
@RequestMapping("appointments")
public class AppointmentController {

    private final AppointmentSchedule appointmentSchedule;

    public AppointmentController(AppointmentSchedule appointmentSchedule) {
        this.appointmentSchedule = appointmentSchedule;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<AppointmentDetailData> scheduleAppointment(@RequestBody @Valid AppointmentSchedulingData data) {
        AppointmentDetailData appointment = appointmentSchedule.scheduleAppointment(data);
        return ResponseEntity.ok(appointment);
    }

    @GetMapping
    public ResponseEntity<Page<AppointmentDetailData>> listAppointments(@PageableDefault(size = 10) Pageable pagination) {
        Page<AppointmentDetailData> dtoPage = appointmentSchedule.listAppointments(pagination);
        return ResponseEntity.ok(dtoPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDetailData> getAppointmentById(@PathVariable Long id) {
        AppointmentDetailData appointment = appointmentSchedule.getAppointmentById(id);
        return ResponseEntity.ok(appointment);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<AppointmentDetailData> updateAppointment(@RequestBody @Valid AppointmentUpdateData data) {
        return ResponseEntity.ok(appointmentSchedule.updateAppointment(data));
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<Void> deleteAppointment(@RequestBody @Valid AppointmentCancelingData data) {
        appointmentSchedule.deleteAppointment(data);
        return ResponseEntity.noContent().build();
    }
}