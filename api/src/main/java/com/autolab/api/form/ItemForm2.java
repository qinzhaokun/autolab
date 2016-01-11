package com.autolab.api.form;

import com.autolab.api.exception.UtilException;
import com.autolab.api.model.Course;
import com.autolab.api.model.Item;
import com.autolab.api.repository.CourseDao;
import com.autolab.api.util.AppContextManager;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.context.ApplicationContext;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.autolab.api.form.Time;

/**
 * Created by ABC on 2015/11/6 0006.
 */

@Data
public class ItemForm2 {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private Long courseId;

    @NotNull
    private String place;

    @NotNull
    private List<Time> times;

    @NotNull
    private String openTime;

    @NotNull
    private Integer allowNumber;

    public Item generateItem(){
        Item item = new Item();
        item.setName(name);
        item.setPlace(place);
        item.setOpenTime(openTime);
        ApplicationContext applicationContext = AppContextManager.getAppContext();
        CourseDao courseDao = applicationContext.getBean(CourseDao.class);
        Course course = courseDao.findOne(courseId);
        if(course == null){
            throw new UtilException("course is not exit");
        }
        item.setCourse(course);
        return item;
    }

    public void updateItem(Item item){
        if(name != null){
            item.setName(name);
        }
        if(place != null){
            item.setPlace(place);
        }
        if(openTime != null){
            item.setOpenTime(openTime);
        }
        if(courseId != null){
            ApplicationContext applicationContext = AppContextManager.getAppContext();
            CourseDao courseDao = applicationContext.getBean(CourseDao.class);
            Course course = courseDao.findOne(courseId);
            if(course == null){
                throw new UtilException("course not exists");
            }
            item.setCourse(course);
        }
        if(times != null){
            throw new UtilException("can not modify time");
        }

    }
}
