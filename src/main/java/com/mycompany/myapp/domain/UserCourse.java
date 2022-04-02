package com.mycompany.myapp.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user_course")
@NoArgsConstructor
@Data
public class UserCourse {
    public UserCourse(User user, Course course) {
        this.user = user;
        this.course = course;
    }
    @Id //primary key annotation
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @ManyToOne
    private User user;

    @JoinColumn(name = "course_id" /*column in user_course*/, referencedColumnName = "id"/*id column in user table*/)
    @ManyToOne
    private Course course;
}
