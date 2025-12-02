package tn.esprit.studentmanagement.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.studentmanagement.entities.Student;
import tn.esprit.studentmanagement.repositories.StudentRepository;
import tn.esprit.studentmanagement.services.StudentService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class StudentIntegrationTest {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Test
    void saveStudent_ShouldPersistInDatabase() {
        Student student = new Student();
        student.setFirstName("Ahmed");
        student.setLastName("Ben Ali");
        student.setEmail("ahmed@example.com");
        student.setPhone("12345678");
        student.setDateOfBirth(LocalDate.of(2000, 5, 15));
        student.setAddress("Tunis");

        Student saved = studentService.saveStudent(student);

        assertNotNull(saved.getIdStudent());
        
        // Verify it's really in the database
        Student fromDb = studentRepository.findById(saved.getIdStudent()).orElse(null);
        assertNotNull(fromDb);
        assertEquals("Ahmed", fromDb.getFirstName());
        assertEquals("ahmed@example.com", fromDb.getEmail());
    }

    @Test
    void getAllStudents_ShouldReturnSavedStudents() {
        Student s1 = new Student();
        s1.setFirstName("Student1");
        s1.setLastName("Test");
        s1.setEmail("s1@test.com");
        
        Student s2 = new Student();
        s2.setFirstName("Student2");
        s2.setLastName("Test");
        s2.setEmail("s2@test.com");

        studentRepository.save(s1);
        studentRepository.save(s2);

        List<Student> students = studentService.getAllStudents();

        assertTrue(students.size() >= 2);
    }

    @Test
    void getStudentById_ShouldReturnCorrectStudent() {
        Student student = new Student();
        student.setFirstName("Test");
        student.setLastName("User");
        student.setEmail("test@example.com");
        Student saved = studentRepository.save(student);

        Student found = studentService.getStudentById(saved.getIdStudent());

        assertNotNull(found);
        assertEquals("Test", found.getFirstName());





    }


















}
