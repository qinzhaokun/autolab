package com.autolab.api.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Item.class)
public abstract class Item_ extends com.autolab.api.model.BaseEntity_ {

	public static volatile ListAttribute<Item, Batch> batches;
	public static volatile SingularAttribute<Item, String> name;
	public static volatile SingularAttribute<Item, Course> course;
	public static volatile SingularAttribute<Item, String> place;
	public static volatile SingularAttribute<Item, String> openTime;

}

