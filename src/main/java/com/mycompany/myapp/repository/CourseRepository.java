package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course /*要操作的表所对应的model class*/, Long/*primary key type*/> {
    //根据StringData JPA的命名规则来命名函数，hibernate就会根据函数的名字帮你生成对应的sql，从而实现这个函数。
    Optional<Course> findByCourseName(String courseName);
}
