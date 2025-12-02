package tn.esprit.studentmanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.entities.Status;
import tn.esprit.studentmanagement.repositories.EnrollmentRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class EnrollmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllEnrollment_ShouldReturnList() throws Exception {
        mockMvc.perform(get("/Enrollment/getAllEnrollment"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createEnrollment_ShouldReturnCreatedEnrollment() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setGrade(15.0);
        enrollment.setStatus(Status.ACTIVE);

        mockMvc.perform(post("/Enrollment/createEnrollment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(enrollment)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    void getEnrollment_ShouldReturnEnrollment() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(Status.COMPLETED);
        Enrollment saved = enrollmentRepository.save(enrollment);

        mockMvc.perform(get("/Enrollment/getEnrollment/" + saved.getIdEnrollment()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void updateEnrollment_ShouldReturnUpdatedEnrollment() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(Status.ACTIVE);
        Enrollment saved = enrollmentRepository.save(enrollment);

        saved.setStatus(Status.COMPLETED);

        mockMvc.perform(put("/Enrollment/updateEnrollment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }

    @Test
    void deleteEnrollment_ShouldReturn200() throws Exception {
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(Status.DROPPED);
        Enrollment saved = enrollmentRepository.save(enrollment);

        mockMvc.perform(delete("/Enrollment/deleteEnrollment/" + saved.getIdEnrollment()))
                .andExpect(status().isOk());
    }
}
