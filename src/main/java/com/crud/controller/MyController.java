package com.crud.controller;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.crud.dao.CourseDao;
import com.crud.entity.Course;
import com.crud.services.CourseService;

@RestController
public class MyController {

	private static final Logger LOGGER = Logger.getLogger(MyController.class.getName());
	@Autowired
	private CourseService courseService;

	@Autowired
	private CourseDao courseDao;

	@GetMapping("/home")
	public String home() {
		return "This is home page";
	}

	@GetMapping("/courses")
	public List<Course> getCourses() {
		// Custom finder menthod implementation
		// ----------------------------------------------
		List<Course> c1 = courseDao.findByTitle("java");
//		for(Course c2:c1)
//		{
//			System.out.println(c2);
//		}
		System.out.println("---------------------Custom_Method_findByTitle()------------------------");
		c1.forEach(e -> System.out.println(e));
		System.out.println("Custom method findByTitle :: " + c1);
		System.out.println("--------------------------------------------------------");
		
		System.out.println("---------------------Custom_Method_getAllCourse()------------------------");
		courseDao.getAllCourse().forEach(e -> {
			System.out.println(e);
			
		});
		System.out.println("--------------------------------------------------------");
		// ----------------------------------------------
		return this.courseService.getCourses();
	}

	@GetMapping("/course/{id}")
	public ResponseEntity<Object> getCourse(@PathVariable String id) {
		Optional<Course> course = this.courseService.getCourse(Long.parseLong(id));
		if (course.isPresent()) {
			return ResponseEntity.ok(course.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with id " + id + " is not found.");
		}
	}

	
	@PostMapping("/courses")
	public ResponseEntity<Object> addCourse(@RequestBody Course course) {

		List<Course> findAll = courseDao.findAll();
		Course existingCourse = null;
		for (Course c : findAll) {
			if (c.getId() == course.getId()) {
				existingCourse = c;
				break;
			}
		}
		if (existingCourse != null) {
			LOGGER.info("Course with id " + existingCourse.getId() + " is already exist.");
			return ResponseEntity.status(HttpStatus.CONFLICT)
					.body("Course with id " + existingCourse.getId() + " is already exist.");

		}
		Optional<Course> addedCourse = courseService.addCourse(course);

		if (addedCourse.isPresent() && addedCourse.get() != null) {
			LOGGER.info("Course added successfully.");
			return ResponseEntity.status(HttpStatus.OK).body(addedCourse.get());
		} else {
			LOGGER.warning("New Course could not be added");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("New Course could not be added");
		}
	}
//-------------------------------------------
// Update Course by using two ways
	
	@PutMapping("/courses")
	public Course updateCourse(@RequestBody Course course) {
		return this.courseService.updateCourse(course);
	}
	
	@PutMapping("/courses/{id}")
	public ResponseEntity<Object> updateCourseById(@PathVariable Long id, @RequestBody Course course) {
		Optional<Course> updateCourse = courseService.updateCourse(id,course);
		
		if(updateCourse.isPresent())
		{
			if(course.getTitle()!=null && course.getDescription()!=null)
			{ 
				return ResponseEntity.status(HttpStatus.OK).body(updateCourse.get());
			}
			else
			{
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Parameter's value can not be null");
			}
		}
		else
		{
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Course with ID " + id + " not found.");
		}
	}
//------------------------------------------
	
	
	@DeleteMapping("/courses/{id}")
	public ResponseEntity<Object> deleteCourse(@PathVariable String id) {
		Optional<Course> findById = courseDao.findById(Long.parseLong(id));
		if (findById.isPresent()) {
			this.courseService.deleteCourse(Long.parseLong(id));
			return ResponseEntity.ok("Successfully deleted...");
		} else {
			String errorMessage = "Course with ID " + id + " not found.";
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage); 
		}

	}

}
