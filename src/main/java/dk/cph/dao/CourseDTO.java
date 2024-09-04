package dk.cph.dao;

import dk.cph.model.Student;
import dk.cph.model.Teacher;
import lombok.Getter;

import java.util.Date;
import java.util.Set;

public class CourseDTO {

    private String name;

    private String courseName;

    private Date startDate;

    private Date endDate;

    @Getter
    private Set<Student> students;

    private Teacher teacher;

}
