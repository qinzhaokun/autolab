package com.autolab.api.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Batch.class)
public abstract class Batch_ extends com.autolab.api.model.BaseEntity_ {

	public static volatile SingularAttribute<Batch, Item> item;
	public static volatile SingularAttribute<Batch, Integer> week;
	public static volatile ListAttribute<Batch, Book> books;
	public static volatile SingularAttribute<Batch, Publish> publish;
	public static volatile SingularAttribute<Batch, Integer> allowNumber;
	public static volatile SingularAttribute<Batch, Date> startTime;
	public static volatile SingularAttribute<Batch, Date> endTime;

}

