package tn.esprit.studentmanagement.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tn.esprit.studentmanagement.entities.Course;
import tn.esprit.studentmanagement.repositories.CourseRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long idCourse) {
        return courseRepository.findById(idCourse).orElse(null);
    }

    @Override
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long idCourse) {
        courseRepository.deleteById(idCourse);
    }
}
