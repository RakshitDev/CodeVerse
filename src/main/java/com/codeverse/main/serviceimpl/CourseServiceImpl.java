package com.codeverse.main.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codeverse.main.entities.Course;
import com.codeverse.main.repository.CourseRepository;
import com.codeverse.main.service.CourseService;

@Service
public class CourseServiceImpl  implements CourseService{

		@Autowired
		public CourseRepository courseRepository;
		
		public List<Course> findAllCourse() {
			List<Course> courseList = courseRepository.findAll();
			return courseList;
		}
}
