package com.crud.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.crud.entity.Course;

@Repository
public interface CourseDao extends JpaRepository<Course,Long>{

	//Optional<Course> findById(Long id);
	
	List<Course> findByTitle(String title);
	
	//void deleteById(Long id);
	
	@Query(value = "select * from course",nativeQuery = true)
	public List<Course> getAllCourse();
}
