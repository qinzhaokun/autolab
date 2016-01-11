package com.autolab.api.repository;

import com.autolab.api.model.Course;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by KUN on 2015/10/24.
 */
public interface CourseDao extends BaseDao<Course, Long>{
    List<Course> findAll(Specification<Course> spec);
}
