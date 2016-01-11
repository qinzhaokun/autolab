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
@Table(name = Item.TABLE_NAME)
public class Item extends BaseEntity{
    public static final String TAG = Item.class.getSimpleName().toLowerCase();
    public static final String TAGS = TAG + "s";
    public static final String TABLE_NAME=BaseEntity.PREFIX+"item";

    private String name;

    @ManyToOne(cascade = {},fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = true)
    private Course course;

    private String place;

    private String openTime;

    @JsonIgnore
    @OneToMany(mappedBy = "item", cascade = {}, fetch = FetchType.LAZY)
    private List<Batch> batches = new ArrayList<>();
}
