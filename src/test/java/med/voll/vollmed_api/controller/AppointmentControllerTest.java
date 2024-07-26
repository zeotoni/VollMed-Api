package med.voll.vollmed_api.controller;

import med.voll.vollmed_api.domain.appointment.AppointmentDetailData;
import med.voll.vollmed_api.domain.appointment.AppointmentSchedule;
import med.voll.vollmed_api.domain.appointment.AppointmentSchedulingData;
import med.voll.vollmed_api.domain.doctor.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<AppointmentSchedulingData> appointmentSchedulingDataJson;

    @Autowired
    private JacksonTester<AppointmentDetailData> appointmentDetailDataJson;

    @MockBean
    private AppointmentSchedule appointmentSchedule;

    @Test
    @DisplayName("Should return http code 400 when information is invalid")
    @WithMockUser
    void scheduleAppointmentCase1() throws Exception {
        var response = mockMvc.perform(post("/appointments"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Should return http code 200 when information is valid")
    @WithMockUser
    void scheduleAppointmentCase2() throws Exception {

        var date = LocalDateTime.now().plusHours(1);
        var specialty = Specialty.CARDIOLOGY;

        var detailData = new AppointmentDetailData(null, 2l, 5l, date);

        when(appointmentSchedule.scheduleAppointment(any())).thenReturn(detailData);

        var response = mockMvc
                .perform(post("/appointments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(appointmentSchedulingDataJson.write(
                                new AppointmentSchedulingData(2l,5l, date, specialty)
                        ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var expectJson = appointmentDetailDataJson.write(detailData).getJson();
    }
}