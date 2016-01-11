package com.autolab.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ABC on 2015/11/9 0009.
 */

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = CourseTeacher.TABLE_NAME)
public class CourseTeacher extends BaseEntity{
    public static final String TAG = Course.class.getSimpleName().toLowerCase();
    public static final String TAGS = TAG + "s";
    public static final String TABLE_NAME=BaseEntity.PREFIX+"course_teacher";


    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;


    @ManyToOne(cascade = {}, fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @JsonIgnore
    @OneToMany(mappedBy = "courseTeacher", cascade = {}, fetch = FetchType.LAZY)
    private List<CourseTeacherStudent> courseTeacherStudents = new ArrayList<>();
}
