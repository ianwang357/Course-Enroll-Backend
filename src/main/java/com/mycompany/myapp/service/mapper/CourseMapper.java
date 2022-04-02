package com.mycompany.myapp.service.mapper;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.service.dto.CourseDTO;
import org.springframework.stereotype.Component;

//Transfer Course -> CourseDTO
@Component
public class CourseMapper {
    public CourseDTO convert(Course course) {
        return CourseDTO.builder()
            .courseName(course.getCourseName())
            .courseLocation(course.getCourseLocation())
            .courseContent(course.getCourseContent())
            .teacherId(course.getTeacherId())
            .build();
    }
}
