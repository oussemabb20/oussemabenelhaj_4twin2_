package tn.esprit.studentmanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.repositories.StudentRepository;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllStudents_ShouldReturnList() throws Exception {
        mockMvc.perform(get("/students/getAllStudents"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createStudent_ShouldReturnCreatedStudent() throws Exception {
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("User");
        student.setEmail("test@controller.com");
        student.setPhone("99999999");
        student.setDateOfBirth(LocalDate.of(2000, 1, 1));

        mockMvc.perform(post("/students/createStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Test"));
    }

    @Test
    void getStudent_ShouldReturnStudent() throws Exception {
        Student student = new Student();
        student.setFirstName("GetTest");
        student.setEmail("gettest@test.com");
        Student saved = studentRepository.save(student);

        mockMvc.perform(get("/students/getStudent/" + saved.getIdStudent()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("GetTest"));
    }

    @Test
    void updateStudent_ShouldReturnUpdatedStudent() throws Exception {
        Student student = new Student();
        student.setFirstName("Before");
        student.setEmail("update@test.com");
        Student saved = studentRepository.save(student);

        saved.setFirstName("After");

        mockMvc.perform(put("/students/updateStudent")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("After"));
    }

    @Test
    void deleteStudent_ShouldReturn200() throws Exception {
        Student student = new Student();
        student.setFirstName("ToDelete");
        student.setEmail("delete@test.com");
        Student saved = studentRepository.save(student);

        mockMvc.perform(delete("/students/deleteStudent/" + saved.getIdStudent()))
                .andExpect(status().isOk());
    }
}
