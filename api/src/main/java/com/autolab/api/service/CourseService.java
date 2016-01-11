package com.autolab.api.service;

import com.autolab.api.exception.UtilException;
import com.autolab.api.model.*;
import com.autolab.api.repository.CourseDao;
import com.autolab.api.repository.CourseTeacherDao;
import com.autolab.api.repository.CourseTeacherStudentDao;
import com.autolab.api.repository.UserDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ABC on 2015/11/9 0009.
 */

@Service
public class CourseService {

    private static Logger logger = Logger.getLogger(CourseService.class);

    @Autowired
    private CourseTeacherDao courseTeacherDao;

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseTeacherStudentDao courseTeacherStudentDao;

    public boolean checkAuth(User user, Course course){
        return true;
    }

    /**
     *
     * @param course
     */
    public void createCourse(Course course){
        //do not set courseTeacher, due to we add those information in database by hand or use url: /course/addteacher/courseId
        courseDao.save(course);
    }


    /**
     *
     * @param course
     * @param teacher
     */
    public void addTeacher(Course course, User teacher){
        CourseTeacher courseTeacher = courseTeacherDao.findByCourseAndTeacher(course,teacher);
        if(courseTeacher != null){
            throw new UtilException("the teacher is already in the course");
        }
        courseTeacher.setCourse(course);
        courseTeacher.setTeacher(teacher);
        courseTeacherDao.save(courseTeacher);
        List<CourseTeacher> courseTeachers = course.getCourseTeachers();
        if(courseTeachers == null){
            throw new UtilException("database error");
        }
        courseTeachers.add(courseTeacher);
        course.setCourseTeachers(courseTeachers);
        courseDao.save(course);
    }

    public List<CourseTeacherStudent> getStudentGrades(Course course, User teacher){
        CourseTeacher courseTeacher = courseTeacherDao.findByCourseAndTeacher(course, teacher);
        if(courseTeacher == null){
            throw new UtilException("the teacher not belongs to this course");
        }
        List<CourseTeacherStudent> courseTeacherStudents = courseTeacher.getCourseTeacherStudents();
        return courseTeacherStudents;
    }

    public List<CourseTeacher> getTeachersByCourse(Course course){
        return courseTeacherDao.findByCourse(course);
    }

    public CourseTeacher getCourseTeacherByCourseAndTeacher(Course course,User teacher){
        return courseTeacherDao.findByCourseAndTeacher(course, teacher);
    }

    public List<Book> getStudentGradeDetailByStudentAndCourseTeacherId(User student, Long courseTeacherId){
        CourseTeacher courseTeacher = courseTeacherDao.findOne(courseTeacherId);
        if(courseTeacher == null){
            throw new UtilException("courseTeacher not exists");
        }
        if(courseTeacherStudentDao.findByCourseTeacherAndStudent(courseTeacher,student) == null){
            throw new UtilException("the student not belongs to this courseTeacher");
        }

        List<Book> books = student.getBooks();
        Course course = courseTeacher.getCourse();
        books = books.stream().filter(book -> book.getBatch().getItem().getCourse().equals(course)).collect(Collectors.toList());
        return books;
    }

    @Transactional(rollbackOn = UtilException.class)
    public void setGrade3(Long[] couserIds,Long []teacherIds, Long[] studentIds, String []grades){

        for(int i = 0;i < teacherIds.length;i++){
            Course course = courseDao.findOne(couserIds[i]);
            User teacher = userDao.findOne(teacherIds[i]);
            CourseTeacher courseTeacher= courseTeacherDao.findByCourseAndTeacher(course,teacher);
            User student = userDao.findOne(studentIds[i]);
            CourseTeacherStudent courseTeacherStudent= courseTeacherStudentDao.findByCourseTeacherAndStudent(courseTeacher,student);
            courseTeacherStudent.setGrade(grades[i]);
            courseTeacherStudentDao.save(courseTeacherStudent);
        }
    }
}
