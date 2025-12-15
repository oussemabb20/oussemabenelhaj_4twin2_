package tn.esprit.studentmanagement.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.studentmanagement.entities.Course;
import tn.esprit.studentmanagement.services.ICourseService;

import java.util.List;

@RestController
@RequestMapping("/Course")
@AllArgsConstructor
public class CourseController {
    private ICourseService courseService;

    @GetMapping("/getAllCourse")
    public List<Course> getAllCourse() {
        return courseService.getAllCourses();
    }

    @GetMapping("/getCourse/{id}")
    public Course getCourse(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PostMapping("/createCourse")
    public Course createCourse(@RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    @PutMapping("/updateCourse")
    public Course updateCourse(@RequestBody Course course) {
        return courseService.saveCourse(course);
    }

    @DeleteMapping("/deleteCourse/{id}")
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }
}
