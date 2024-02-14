package com.crud.services;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.dao.CourseDao;
import com.crud.entity.Course;

@Service
public class CourseServiceImpl implements CourseService{

	private static final Logger LOGGER = Logger.getLogger(CourseServiceImpl.class.getName());
	@Autowired
	private CourseDao courseDao;
	
	@Override
	public List<Course> getCourses() {
		return this.courseDao.findAll();
	}

	@Override
	public Optional<Course> getCourse(Long id) {
		
		
		return this.courseDao.findById(id);
	}

	@Override
	public Optional<Course> addCourse(Course course) {
	    try {
	        Course savedCourse = courseDao.save(course);
	        LOGGER.info("Course saved successfully.");
	        return Optional.ofNullable(savedCourse); // Return the saved course or null
	    } catch (Exception e) {
	        LOGGER.severe("Error adding course: " + e.getMessage());
	        // Handle the exception here, e.g., log it
	        return Optional.empty(); // Return an empty Optional to indicate failure
	    }
	}

	@Override
	public Course updateCourse(Course course) {
		
	return this.courseDao.save(course);
}
	
	@Override
	public Optional<Course> updateCourse(Long id,Course course) {
		Optional<Course> findById = courseDao.findById(id);
		if(findById.isPresent())
		{
			Course existingCourse = findById.get();
			existingCourse.setTitle(course.getTitle());
			existingCourse.setDescription(course.getDescription());
			courseDao.save(existingCourse);
			return Optional.of(existingCourse);
		
		}
		else
		{
			System.out.println("Course with id not found.");
		}
		return Optional.empty();
	}

	@Override
	public void deleteCourse(Long id) {
	//	Optional<Course> findById = courseDao.findById(id);
		this.courseDao.deleteById(id);
	}

	

}
