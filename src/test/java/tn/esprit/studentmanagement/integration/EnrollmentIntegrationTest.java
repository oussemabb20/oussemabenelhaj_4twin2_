package tn.esprit.studentmanagement.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.studentmanagement.entities.Enrollment;
import tn.esprit.studentmanagement.entities.Status;
import tn.esprit.studentmanagement.repositories.EnrollmentRepository;
import tn.esprit.studentmanagement.services.EnrollmentService;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EnrollmentIntegrationTest {

    @Autowired
    private EnrollmentService enrollmentService;

    @Autowired
    private EnrollmentRepository enrollmentRepository;

    @Test
    void saveEnrollment_ShouldPersistInDatabase() {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(LocalDate.now());
        enrollment.setGrade(15.5);
        enrollment.setStatus(Status.ACTIVE);

        Enrollment saved = enrollmentService.saveEnrollment(enrollment);

        assertNotNull(saved.getIdEnrollment());
        
        Enrollment fromDb = enrollmentRepository.findById(saved.getIdEnrollment()).orElse(null);
        assertNotNull(fromDb);
        assertEquals(Status.ACTIVE, fromDb.getStatus());
        assertEquals(15.5, fromDb.getGrade());
    }

    @Test
    void getAllEnrollments_ShouldReturnSavedEnrollments() {
        Enrollment e1 = new Enrollment();
        e1.setStatus(Status.ACTIVE);
        
        Enrollment e2 = new Enrollment();
        e2.setStatus(Status.COMPLETED);

        enrollmentRepository.save(e1);
        enrollmentRepository.save(e2);

        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();

        assertTrue(enrollments.size() >= 2);
    }

    @Test
    void getEnrollmentById_ShouldReturnCorrectEnrollment() {
        Enrollment enrollment = new Enrollment();
        enrollment.setStatus(Status.ACTIVE);
        enrollment.setGrade(18.0);
        Enrollment saved = enrollmentRepository.save(enrollment);

        Enrollment found = enrollmentService.getEnrollmentById(saved.getIdEnrollment());

        assertNotNull(found);
        assertEquals(18.0, found.getGrade());
    }

}
