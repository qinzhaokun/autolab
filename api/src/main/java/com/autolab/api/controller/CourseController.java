package com.autolab.api.controller;

/**
 * Created by KUN on 2015/10/24.
 */

import com.autolab.api.exception.UtilException;
import com.autolab.api.form.CourseForm;
import com.autolab.api.form.GradeForm2;
import com.autolab.api.form.GradeForm3;
import com.autolab.api.model.*;
import com.autolab.api.repository.BookDao;
import com.autolab.api.repository.CourseDao;
import com.autolab.api.repository.CourseTeacherDao;
import com.autolab.api.repository.CourseTeacherStudentDao;
import com.autolab.api.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/course")
public class CourseController  extends BaseController{
    public static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    protected CourseDao courseDao;

    @Autowired
    protected BookDao bookDao;

    @Autowired
    protected  CourseTeacherDao courseTeacherDao;

    @Autowired
    protected CourseService courseService;

    @Autowired
    protected CourseTeacherStudentDao courseTeacherStudentDao;

    /**
     * create a new course
     * @param form
     * @return
     */
    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value = "/create")
    public Map<String,?> create(@Valid CourseForm form){
        User user = getUser();
        Course course = form.generateCourse();
        courseService.createCourse(course);
        return success(Course.TAG, course);
    }

    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value = "/addteacher/{courseId}")
    public Map<String, ?> addTeacher(@PathVariable Long courseId,
                                     @RequestParam(required = true) Long teacherId){
        Course course = courseDao.findOne(courseId);
        if(course == null){
            throw new UtilException("course not exists");
        }
        User teacher = userDao.findOne(teacherId);
        if(teacher == null){
            throw new UtilException("teacher not exist");
        }
        courseService.addTeacher(course, teacher);
        return success(Course.TAG, course);
    }

    /**
     * edit a course
     *@param form
     * @return
     */
    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value = "/edit")
    public Map<String, ?> edit(CourseForm form) {

        if (form.getId() == null) {
            throw new UtilException("id required!");
        }
        Course course = courseDao.findOne(form.getId());

        form.updateCourse(course);

        courseDao.save(course);

        return success(Course.TAG, course);
    }

    /**
     * detele a course
     * @param courseId
     * @return
     */
    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value = "/del/{courseId}")
    public Map<String, ?> del(@PathVariable Long courseId) {

        Course course = courseDao.findOne(courseId);

        if(course == null){
            throw new UtilException("course is not exit");
        }

        course.setStatus(Status.DELETED);

        courseDao.save(course);

        return success();
    }

    /**
     * fina a course
     * @param courseId
     * @return
     */
    @RequestMapping(value = "/detail/{courseId}")
    public Map<String, ?> find(@PathVariable Long courseId) {
        Course course = courseDao.findOne(courseId);

        if(course == null){
            throw new UtilException("course is not exit");
        }
        return success(Course.TAG, course);
    }

    @RequestMapping(value = "/page")
    public Map<String, ?> page(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "id") String orderBy,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String term,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction
    ) {
        Pageable pageable = new PageRequest(page, size, new Sort(direction,orderBy));
        Page<Course> courses = courseDao.findAll((root,query,cb) -> {
            Predicate predicate = setStatusNotDeleted(root, cb);
            if(name != null){
                predicate = cb.and(predicate,cb.equal(root.get(Course_.name),name));
            }
            if(term != null){
                predicate = cb.and(predicate,cb.equal(root.get(Course_.term),term));
            }

            return predicate;
        },pageable);
        return success(Course.TAGS, courses, pageable);
    }

    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
     @RequestMapping(value = "/teachers/{courseId}")
     public Map<String, ?> getTeachers(@PathVariable Long courseId){
        Course course = courseDao.findOne(courseId);
        if(course == null){
            throw new UtilException("course not exits");
        }
        List<CourseTeacher> courseTeachers = courseService.getTeachersByCourse(course);

        return success(CourseTeacher.TAGS,courseTeachers);
    }

    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value = "/studentgrades")
    public Map<String, ?> getStudentGrades(
            @RequestParam(required = true) Long courseId,
            @RequestParam(required = true) Long teacherId){
        Course course = courseDao.findOne(courseId);
        if(course == null){
            throw new UtilException("course not exits");
        }
        User teacher = userDao.findById(teacherId);
        List<CourseTeacherStudent> courseTeacherStudents = courseService.getStudentGrades(course, teacher);

        return success(CourseTeacherStudent.TAGS,courseTeacherStudents);
    }

    //@PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value = "/studentgradedetail")
    public Map<String, ?> getStudentGradeDetail(
            @RequestParam(required = true) Long studentId,
            @RequestParam(required = true) Long courseId,
            @RequestParam(required = true) Long teacherId){
        User student = userDao.findOne(studentId);
        if(student == null){
            throw new UtilException("student not exists");
        }
        Course course = courseDao.findOne(courseId);
        User teacher = userDao.findOne(teacherId);
        CourseTeacher courseTeacher = courseTeacherDao.findByCourseAndTeacher(course, teacher);

        List<Book> books = courseService.getStudentGradeDetailByStudentAndCourseTeacherId(student,courseTeacher.getId());

        return success(Book.TAGS,books);
    }

    /**
     * teacher browse the books
     *  @param form
     * @return page
     */

    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value =  "/grades3")
    public Map<String,?> setGrades2(@Valid GradeForm3 form){

        courseService.setGrade3( form.getCourseIds(),form.getTeacherIds(),form.getStudentIds(),form.getGrades());

        return success();

    }

    @PreAuthorize(User.Role.HAS_ROLE_USER)
    @RequestMapping(value = "/mygrade")
    public Map<String,?> getMygrade(
    ){
        User student = getUser();
        List<CourseTeacherStudent> courseTeacherStudents = courseTeacherStudentDao.findByStudent(student);

        return success(CourseTeacherStudent.TAGS,courseTeacherStudents);
    }

    @PreAuthorize(User.Role.HAS_ROLE_USER)
    @RequestMapping(value = "/mygradedetail")
    public Map<String,?> getMygradeDetail(
            @RequestParam(required = true) Long courseId
    ){
        User student = getUser();
        Course course = courseDao.findOne(courseId);
        List<Book> books = student.getBooks();
        books = books.stream().filter(book -> book.getBatch().getItem().getCourse().equals(course)).collect(Collectors.toList());

        return success(Book.TAGS,books);
    }
}
