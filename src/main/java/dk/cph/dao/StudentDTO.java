package dk.cph.dao;

import dk.cph.model.Course;
import lombok.Getter;

import java.util.Set;

public class StudentDTO {

    private String name;

    private String email;

    @Getter
    Set<Course> courses;

}
