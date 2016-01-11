package com.autolab.api.controller;

import com.autolab.api.exception.UtilException;
import com.autolab.api.model.*;
import com.autolab.api.repository.CourseDao;
import com.autolab.api.repository.ItemDao;
import com.autolab.api.service.CourseService;
import com.autolab.api.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.autolab.api.form.ItemForm;
import com.autolab.api.form.ItemForm2;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.Map;

/**
 * Created by KUN on 2015/10/24.
 */

@RestController
@RequestMapping("/item")
public class ItemController extends BaseController{
    public static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    @Autowired
    protected ItemDao itemDao;

    @Autowired
    protected CourseDao courseDao;

    @Autowired
    protected ItemService itemService;

    @Autowired
    protected CourseService courseService;
    /**
     * create a item
     * @param form
     * @return
     */
    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value = "/create") //name,place,opentime,allownumber
    public Map<String,?> create(@Valid ItemForm form){
        Item item = form.generateItem();
        itemService.createItem(item, form.getAllowNumber());
        return success(Item.TAG, item);
    }

    /**
     * create a item
     * @param form2
     * @return
     */
    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value = "/create2") //name,place,time,allowNumber,courseId
    public Map<String,?> create2(@Valid @RequestBody ItemForm2 form2){
        Item item = form2.generateItem();
        itemService.createItem2(item, form2.getAllowNumber(),form2.getTimes());
        return success(Item.TAG, item);
    }

    /**
     * edit a course
     *@param form2
     * @return
     */
    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value = "/edit")
    public Map<String, ?> edit(ItemForm2 form2) {

        if (form2.getId() == null) {
            throw new UtilException("id required!");
        }
        Item item = itemDao.findByIdAndStatus(form2.getId(),Status.OK);

        if(item == null){
            throw  new UtilException("item not exits");
        }

        form2.updateItem(item);

        itemDao.save(item);

        return success(Item.TAG, item);
    }

    /**
     * detele a course
     * @param itemId
     * @return
     */
    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value = "/del/{itemId}")
    public Map<String, ?> del(@PathVariable Long itemId) {

        Item item = itemDao.findByIdAndStatus(itemId, Status.OK);

        if(item == null){
            throw new UtilException("item is not exit");
        }

        item.setStatus(Status.DELETED);

        itemDao.save(item);

        return success();
    }

    /**
     * find a item
     * @param itemId
     * @return
     */
    @RequestMapping(value = "/detail/{itemId}")
    public Map<String, ?> find(@PathVariable Long itemId) {
        Item item = itemDao.findByIdAndStatus(itemId, Status.OK);

        if(item == null){
            throw new UtilException("item is not exit");
        }
        return success(Item.TAG, item);
    }

    @RequestMapping(value = "/page")
    public Map<String, ?> page(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = false, defaultValue = "id") String orderBy,
            @RequestParam(required = false) Long courseId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction
    ) {
        Pageable pageable = new PageRequest(page, size, new Sort(direction,orderBy));
        Page<Item> items = itemDao.findAll((root,query,cb) -> {
            Predicate predicate = setStatusNotDeleted(root, cb);
            if(courseId != null){
                Course course = courseDao.findOne(courseId);
                predicate = cb.and(predicate,cb.equal(root.get(Item_.course),course));
            }
            if(name != null){
                predicate = cb.and(predicate,cb.equal(root.get(Item_.name),name));
            }
            predicate = cb.and(predicate,cb.equal(root.get(Item_.status),Status.OK));
            return predicate;
        },pageable);
        return success(Item.TAGS, items, pageable);
    }



}
