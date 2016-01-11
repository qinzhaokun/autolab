package com.autolab.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CourseTeacher.class)
public abstract class CourseTeacher_ extends com.autolab.api.model.BaseEntity_ {

	public static volatile SingularAttribute<CourseTeacher, User> teacher;
	public static volatile SingularAttribute<CourseTeacher, Course> course;
	public static volatile ListAttribute<CourseTeacher, CourseTeacherStudent> courseTeacherStudents;

}

