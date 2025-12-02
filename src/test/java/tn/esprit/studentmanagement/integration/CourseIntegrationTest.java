package tn.esprit.studentmanagement.integration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.studentmanagement.entities.Course;
import tn.esprit.studentmanagement.repositories.CourseRepository;
import tn.esprit.studentmanagement.services.CourseService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseIntegrationTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Test
    void saveCourse_ShouldPersistInDatabase() {
        Course course = new Course();
        course.setName("Java Programming");
        course.setCode("CS101");
        course.setCredit(3);
        course.setDescription("Introduction to Java");

        Course saved = courseService.saveCourse(course);

        assertNotNull(saved.getIdCourse());

        Course fromDb = courseRepository.findById(saved.getIdCourse()).orElse(null);
        assertNotNull(fromDb);
        assertEquals("Java Programming", fromDb.getName());
    }

    @Test
    void getAllCourses_ShouldReturnSavedCourses() {
        Course c1 = new Course();
        c1.setName("Python");
        c1.setCode("CS102");

        Course c2 = new Course();
        c2.setName("Database");
        c2.setCode("CS103");

        courseRepository.save(c1);
        courseRepository.save(c2);

        List<Course> courses = courseService.getAllCourses();

        assertTrue(courses.size() >= 2);
    }

    @Test
    void getCourseById_ShouldReturnCorrectCourse() {
        Course course = new Course();
        course.setName("Web Development");
        course.setCode("CS104");
        Course saved = courseRepository.save(course);

        Course found = courseService.getCourseById(saved.getIdCourse());

        assertNotNull(found);
        assertEquals("Web Development", found.getName());
    }
}
