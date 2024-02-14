package com.crud.services;

import java.util.List;
import java.util.Optional;

import com.crud.entity.Course;

public interface CourseService {

	public List<Course> getCourses();
	public Optional<Course> getCourse(Long id);
	public Optional<Course> addCourse(Course course);
	public Course updateCourse(Course course);
	public Optional<Course> updateCourse(Long id,Course course);
	public void deleteCourse(Long id);
}
