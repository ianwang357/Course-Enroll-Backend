package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.service.CourseService;
import com.mycompany.myapp.service.dto.CourseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Bean
@RequestMapping(path = "/api") // Put every API under "/api/**". The Spring security will verify jwt token automatically.
@AllArgsConstructor
public class CourseController {
    private CourseService courseService;
    /**
     * 实现学生选课功能
     * HTTP方法：POST(Create)
     * URL: http://localhost:8080/student/course/{courseName}
     * Request header: jwt token
     * Response header: N/A
     * Request body: N/A
     * Response body: N/A
     *
     * Response status: 201 or 204
     */
    @PostMapping(path = "/student/course/{courseName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void enrollCourse(@PathVariable String courseName) {
        String userName = getUsername();
        courseService.enrollCourse(userName, courseName);
    }

    /**
     * 列出所有已有课程
     * HTTP方法：GET(read)
     * URL: http://localhost:8080/courses
     * Request header: jwt token
     * Response header: N/A
     * Request body: N/A
     * Response body: [courses]
     *
     * Response status: 200
     */
    @GetMapping(path = "/courses")
    public List<CourseDTO> listAllCourses() {
        return courseService.listAllCourses();
    }

    /**
     * 列出学生已选课程
     * HTTP方法：Get
     * URL: http://localhost:8080/student/courses
     * Request header: jwt token
     * Response header: N/A
     * Request body: N/A
     * Response body: [student enrolled courses]
     *
     * Response status:
     */
    @GetMapping(path = "/student/courses")
    public List<CourseDTO> getStudentCourses() {
        String userName = getUsername();
        return courseService.getStudentCourses(userName);
    }

    /*
      实现学生删课功能
      HTTP：DELETE(Delete)
      URL：http://localhost:8080/student/course/{courseName}
      Request Header: jwt token
      Response header: N/A
      request body: N/A
      response body: N/A

      response status: 204
     */
    @DeleteMapping(path = "/student/course/{courseName}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void dropCourse(@PathVariable String courseName){
        String userName = getUsername();
        courseService.dropCourse(userName, courseName);
    }

    /*
    Extract username from JWT token
     */
    private String getUsername() {
        String username;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }
        return username;
    }
}
