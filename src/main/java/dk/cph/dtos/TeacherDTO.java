package dk.cph.dtos;

import dk.cph.model.Course;
import lombok.Getter;

import java.util.Set;

public class TeacherDTO {

    private String name;

    private String zoom;

    @Getter
    private Set<Course> courses;

}
