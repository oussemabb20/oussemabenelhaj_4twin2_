package tn.esprit.studentmanagement.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class DepartmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllDepartment_ShouldReturnList() throws Exception {
        mockMvc.perform(get("/Depatment/getAllDepartment"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void createDepartment_ShouldReturnCreatedDepartment() throws Exception {
        Department dept = new Department();
        dept.setName("IT Department");
        dept.setLocation("Building B");
        dept.setPhone("11111111");
        dept.setHead("Dr. Test");

        mockMvc.perform(post("/Depatment/createDepartment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dept)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("IT Department"));
    }

    @Test
    void getDepartment_ShouldReturnDepartment() throws Exception {
        Department dept = new Department();
        dept.setName("GetDept");
        Department saved = departmentRepository.save(dept);

        mockMvc.perform(get("/Depatment/getDepartment/" + saved.getIdDepartment()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("GetDept"));
    }

    @Test
    void updateDepartment_ShouldReturnUpdatedDepartment() throws Exception {
        Department dept = new Department();
        dept.setName("OldName");
        Department saved = departmentRepository.save(dept);

        saved.setName("NewName");

        mockMvc.perform(put("/Depatment/updateDepartment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saved)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("NewName"));
    }

    @Test
    void deleteDepartment_ShouldReturn200() throws Exception {
        Department dept = new Department();
        dept.setName("ToDelete");
        Department saved = departmentRepository.save(dept);

        mockMvc.perform(delete("/Depatment/deleteDepartment/" + saved.getIdDepartment()))
                .andExpect(status().isOk());
    }
}
