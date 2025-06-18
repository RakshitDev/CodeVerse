package com.codeverse.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.codeverse.main.entities.Course;
@Repository
public interface CourseRepository  extends JpaRepository<Course, Long>{

}
