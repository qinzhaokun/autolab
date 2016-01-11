package com.autolab.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Course.class)
public abstract class Course_ extends com.autolab.api.model.BaseEntity_ {

	public static volatile ListAttribute<Course, CourseTeacher> courseTeachers;
	public static volatile SingularAttribute<Course, String> name;
	public static volatile SingularAttribute<Course, String> term;
	public static volatile ListAttribute<Course, Item> items;

}

