package com.mycompany.myapp.service;

import com.mycompany.myapp.domain.Course;
import com.mycompany.myapp.domain.User;
import com.mycompany.myapp.domain.UserCourse;
import com.mycompany.myapp.repository.CourseRepository;
import com.mycompany.myapp.repository.UserCourseRepository;
import com.mycompany.myapp.repository.UserRepository;
import com.mycompany.myapp.service.dto.CourseDTO;
import com.mycompany.myapp.service.mapper.CourseMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CourseService {
    private UserRepository userRepository;
    private CourseRepository courseRepository;
    private UserCourseRepository userCourseRepository;
    private CourseMapper courseMapper;

    public void enrollCourse(String userName, String courseName) {
        // 1. User exists?
        // 2. Course exists?
        UserCourse userCourse = getUserCourse(userName, courseName);
        // 3. UserCourse Not exists? (De-dupe)
        Optional<UserCourse> optionalUserCourse = userCourseRepository.findOneByUserAndCourse(userCourse.getUser(), userCourse.getCourse());
        optionalUserCourse.ifPresent(existingUserCourse -> {
            throw new IllegalArgumentException("UserCourse already exists: " + existingUserCourse.toString());
        });
        //4. Save new UserCourse to DB
        userCourseRepository.save(userCourse);
    }

    private UserCourse getUserCourse(String userName, String courseName) {
        // 1. User exists?
        Optional<User> optionalUser = userRepository.findOneByLogin(userName);
        // throw exception if no such user found
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("No such user: " + userName));

        // 2. Course exists?
        Optional<Course> optionalCourse = courseRepository.findByCourseName(courseName);
        // throw exception if no such course found
        Course course = optionalCourse.orElseThrow(() -> new IllegalArgumentException("No such course: " + courseName));

        return new UserCourse(user, course);
    }

    public List<CourseDTO> listAllCourses() {
        List<Course> courses = courseRepository.findAll();
//        List<CourseDTO> courseDTOList = new ArrayList<>();
//        for (Course course: courses) {
//            courseDTOList.add(courseMapper.convert(course));
//        }
//        return courseDTOList;
        //Java Stream - Java 8 特性
        return courses.stream().map(course -> courseMapper.convert(course)).collect(Collectors.toList());
    }

    public List<CourseDTO> getStudentCourses(String userName) {
        // 1. User exists?
        Optional<User> optionalUser = userRepository.findOneByLogin(userName);
        // throw exception if no such user found
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("No such user: " + userName));
        List<UserCourse> userCourseList = userCourseRepository.findAllByUser(user);
//        List<CourseDTO> courseDTOList = new ArrayList<>();
//        for (UserCourse userCourse: userCourseList) {
//            courseDTOList.add(courseMapper.convert(userCourse.getCourse()));
//        }
//        return courseDTOList;
        return userCourseList.stream()
            .map(userCourse -> userCourse.getCourse())
            .map(course -> courseMapper.convert(course))
            .collect(Collectors.toList());
    }


    public void dropCourse(String userName, String courseName) {
        // 1. User exists?
        // 2. Course exists?
        UserCourse userCourse = getUserCourse(userName, courseName);
        // 不需要check UserCourse表中是否存在此relationship，它只需要保证操作结束后，表中无此数据
        userCourseRepository.deleteByUserAndCourse(userCourse.getUser(), userCourse.getCourse());
    }
}
