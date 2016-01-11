package com.autolab.api.repository;

import com.autolab.api.model.Course;
import com.autolab.api.model.CourseTeacher;
import com.autolab.api.model.User;

import java.util.List;

/**
 * Created by ABC on 2015/11/9 0009.
 */
public interface CourseTeacherDao extends BaseDao<CourseTeacher, Long> {
    List<CourseTeacher> findByTeacher(User teacher);
    List<CourseTeacher> findByCourse(Course course);
    CourseTeacher findByCourseAndTeacher(Course course, User teacher);

}
