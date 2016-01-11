package com.autolab.api.repository;

import com.autolab.api.model.Item;
import com.autolab.api.model.Status;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

/**
 * Created by KUN on 2015/10/24.
 */
public interface ItemDao extends BaseDao<Item, Long>{
    List<Item> findAll(Specification<Item> spec);

    Item findByIdAndStatus(Long id, Status status);
}
