package tn.esprit.studentmanagement.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.studentmanagement.entities.Department;
import tn.esprit.studentmanagement.repositories.DepartmentRepository;
import tn.esprit.studentmanagement.services.DepartmentService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DepartmentIntegrationTest {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Test
    void saveDepartment_ShouldPersistInDatabase() {
        Department dept = new Department();
        dept.setName("Informatique");
        dept.setLocation("Bloc A");
        dept.setPhone("71123456");
        dept.setHead("Dr. Mohamed");

        Department saved = departmentService.saveDepartment(dept);

        assertNotNull(saved.getIdDepartment());
        
        Department fromDb = departmentRepository.findById(saved.getIdDepartment()).orElse(null);
        assertNotNull(fromDb);
        assertEquals("Informatique", fromDb.getName());
    }

    @Test
    void getAllDepartments_ShouldReturnSavedDepartments() {
        Department d1 = new Department();
        d1.setName("Dept1");
        
        Department d2 = new Department();
        d2.setName("Dept2");

        departmentRepository.save(d1);
        departmentRepository.save(d2);

        List<Department> departments = departmentService.getAllDepartments();

        assertTrue(departments.size() >= 2);
    }

    @Test
    void getDepartmentById_ShouldReturnCorrectDepartment() {
        Department dept = new Department();
        dept.setName("Test Dept");
        Department saved = departmentRepository.save(dept);

        Department found = departmentService.getDepartmentById(saved.getIdDepartment());

        assertNotNull(found);
        assertEquals("Test Dept", found.getName());
    }

}
