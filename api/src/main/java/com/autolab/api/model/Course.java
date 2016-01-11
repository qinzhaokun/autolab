package com.autolab.api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by KUN on 2015/10/24.
 */

@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = Course.TABLE_NAME)
public class Course extends BaseEntity{
    public static final String TAG = Course.class.getSimpleName().toLowerCase();
    public static final String TAGS = TAG + "s";
    public static final String TABLE_NAME=BaseEntity.PREFIX+"course";

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = {}, fetch = FetchType.LAZY)
    private List<CourseTeacher> courseTeachers = new ArrayList<>();

    private String name;

    private String term;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = {}, fetch = FetchType.LAZY)
    private List<Item> items = new ArrayList<>();


}
