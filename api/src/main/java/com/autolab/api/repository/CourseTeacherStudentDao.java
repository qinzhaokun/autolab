package com.autolab.api.repository;

import com.autolab.api.model.CourseTeacher;
import com.autolab.api.model.CourseTeacherStudent;
import com.autolab.api.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by KUN on 2015/11/10.
 */
public interface CourseTeacherStudentDao extends BaseDao<CourseTeacherStudent, Long> {
    List<CourseTeacherStudent> findAll(Specification<CourseTeacherStudent> spec);
    List<CourseTeacherStudent> findByCourseTeacher(CourseTeacher courseTeacher);
    List<CourseTeacherStudent> findByStudent(User student);
    CourseTeacherStudent findByCourseTeacherAndStudent(CourseTeacher courseTeacher, User student);
}
