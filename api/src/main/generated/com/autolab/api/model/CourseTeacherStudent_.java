package com.autolab.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CourseTeacherStudent.class)
public abstract class CourseTeacherStudent_ extends com.autolab.api.model.BaseEntity_ {

	public static volatile SingularAttribute<CourseTeacherStudent, User> student;
	public static volatile SingularAttribute<CourseTeacherStudent, String> grade;
	public static volatile SingularAttribute<CourseTeacherStudent, CourseTeacher> courseTeacher;

}

