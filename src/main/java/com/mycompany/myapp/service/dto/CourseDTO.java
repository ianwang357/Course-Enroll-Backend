package com.mycompany.myapp.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//DTO: data transfer object
@Data //Getter/Setter
@Builder
@AllArgsConstructor
public class CourseDTO {
    private String courseName;
    private String courseContent;
    private String courseLocation;
    private Long teacherId;
}
