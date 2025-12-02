package tn.esprit.studentmanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.studentmanagement.entities.Course;
import tn.esprit.studentmanagement.repositories.CourseRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllCourse_ShouldReturnList() throws Exception {
        mockMvc.perform(get("/Course/getAllCourse"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createCourse_ShouldReturnCreatedCourse() throws Exception {
        Course course = new Course();
        course.setName("Spring Boot");
        course.setCode("SB101");
        course.setCredit(4);
        course.setDescription("Learn Spring Boot");

        mockMvc.perform(post("/Course/createCourse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Spring Boot"));
    }

    @Test
    void getCourse_ShouldReturnCourse() throws Exception {
        Course course = new Course();
        course.setName("GetCourse");
        course.setCode("GC101");
        Course saved = courseRepository.save(course);

        mockMvc.perform(get("/Course/getCourse/" + saved.getIdCourse()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("GetCourse"));
    }

    @Test
    void updateCourse_ShouldReturnUpdatedCourse() throws Exception {
        Course course = new Course();
        course.setName("OldCourse");
        course.setCode("OC101");
        Course saved = courseRepository.save(course);

        saved.setName("NewCourse");

        mockMvc.perform(put("/Course/updateCourse")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NewCourse"));
    }

    @Test
    void deleteCourse_ShouldReturn200() throws Exception {
        Course course = new Course();
        course.setName("ToDelete");
        course.setCode("TD101");
        Course saved = courseRepository.save(course);

        mockMvc.perform(delete("/Course/deleteCourse/" + saved.getIdCourse()))
                .andExpect(status().isOk());
    }
}
