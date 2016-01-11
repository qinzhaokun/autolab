package com.autolab.api.form;

import com.autolab.api.model.Course;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by KUN on 2015/10/24.
 */

@Data
public class CourseForm {
    private Long id;

    @NotNull
    private String name;

    @NotNull
    private String term;

    public Course generateCourse(){
        Course course = new Course();
        course.setName(name);
        course.setTerm(term);
        return course;
    }

    public void updateCourse(Course course){
        if(name != null){
            course.setName(name);
        }
        if(term != null){
            course.setTerm(term);
        }

    }
}
