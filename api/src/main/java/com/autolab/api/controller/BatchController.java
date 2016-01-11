package com.autolab.api.controller;

import com.autolab.api.exception.UtilException;
import com.autolab.api.form.BatchForm;
import com.autolab.api.form.GradeForm;
import com.autolab.api.form.GradeForm2;
import com.autolab.api.model.*;
import com.autolab.api.repository.*;
import com.autolab.api.service.BatchService;
import com.autolab.api.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.util.resources.cldr.ta.CalendarData_ta_IN;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by ABC on 2015/10/24 0024.
 */


@RestController
@RequestMapping("/batch")
public class BatchController extends BaseController{
    public static final Logger logger = LoggerFactory.getLogger(BatchController.class);

    @Autowired
    protected BatchDao batchDao;

    @Autowired
    protected ItemDao itemDao;

    @Autowired
    protected BookDao bookDao;

    @Autowired
    protected BatchService batchService;

    @Autowired
    protected CourseDao courseDao;


    @Autowired
    protected CourseService courseService;

    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping("/create")
    public Map<String, ?> create(@Valid BatchForm form){
        Batch batch = form.generateBatch();
        if(!courseService.checkAuth(getUser(), batch.getItem().getCourse())){
            throw new UtilException("you have no authorization");
        }
        batchDao.save(batch);
        return success(Batch.TAG, batch);
    }

    /**
     * edit a batch
     * @param form
     * @return
     */
    //@PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping("/edit")
    public Map<String, ?> edit(BatchForm form){
        if(form.getId() == null){
            throw  new UtilException("id required");
        }
        Batch batch = batchDao.findOne(form.getId());
        if(batch == null){
            throw  new UtilException("batch noe exits");
        }
        form.updateBatch(batch);
        batchDao.save(batch);
        return success(Batch.TAG,batch);
    }


    /**
     *
     * @param batchId
     * @return
     */
    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping("/publish/{batchId}")
    public Map<String, ?> publish(@PathVariable Long batchId){
        Batch batch = batchDao.findByIdAndStatus(batchId, Status.OK);
        if(batch == null){
            throw new UtilException("batch not exits");
        }
        if(!courseService.checkAuth(getUser(),batch.getItem().getCourse())){
            throw new UtilException("you have no authorization to operate other teacher's batch");
        }
        List<Book> books = batch.getBooks();
        int num = books.size();
        for(int i = 0;i < books.size();i++){
            if(books.get(i).getGrade() != null){
                num--;
            }
        }
        if(num != 0){
            throw new UtilException("you have " + num + " students' grade to be set");
        }
        batch.setPublish(Publish.YES);
        batchDao.save(batch);
        return success();
    }

    /**
     * teacher browse the books
     *  @param batchId
     * @return page
     */

    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value =  "/books/{batchId}")
    public Map<String,?> browse(@PathVariable Long batchId,
                                @RequestParam(required = false, defaultValue = "0") Integer page,
                                @RequestParam(required = false, defaultValue = "20") Integer size){
        Batch batch = batchDao.findByIdAndStatus(batchId, Status.OK);
        if(batch == null){
            throw new UtilException("batch not exits");
        }

        if(!courseService.checkAuth(getUser(), batch.getItem().getCourse())){
            throw new UtilException("you have no authorization");
        }
        Pageable pageable = new PageRequest(page, size);

        Page<Book> books = bookDao.findAll(((root, query, cb) -> {
            Predicate predicate = cb.notEqual(root.get(Book_.status), Status.DELETED);
            predicate = cb.and(predicate, cb.equal(root.get(Book_.batch), batch));
            return predicate;
        }) , pageable);



        return success(Book.TAGS, books, pageable);

    }


    /**
     * teacher browse the books
     *  @param form
     * @return page
     */

    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value =  "/grades")
    public Map<String,?> setGrades(@Valid GradeForm form){

        batchService.setGrade(form.getStudentIds(), form.getGrades(), form.getBatchId());

        return success();

    }


    /**
     * teacher browse the books
     *  @param form
     * @return page
     */

    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value =  "/grades2")
    public Map<String,?> setGrades2(@Valid GradeForm2 form){

        batchService.setGrade2(form.getGrades(), form.getBookIds());

        return success();

    }

    /**
     * delete a batch
     * @param batchId
     * @return
     */
    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping("/del/{batchId}")
    public Map<String, ?> del(@PathVariable Long batchId){
        Batch batch = batchDao.findByIdAndStatus(batchId, Status.OK);
        if(!courseService.checkAuth(getUser(), batch.getItem().getCourse())){
            throw new UtilException("you have no authorization");
        }
        if(batch == null){
            throw new UtilException("batch is not exit");
        }

        batch.setStatus(Status.DELETED);

        batchDao.save(batch);

        return success();
    }

    /**
     * fina a batch
     * @param batchId
     * @return
     */
    @RequestMapping(value = "/detail/{batchId}")
    public Map<String, ?> find(@PathVariable Long batchId) {
        Batch batch = batchDao.findOne(batchId);

        if(batch == null){
            throw new UtilException("batch is not exit");
        }
        return success(Batch.TAG, batch);
    }

    @RequestMapping(value = "/page")
    public Map<String, ?> page(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = true) Long itemId,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction
    ) {

        List<Map<String, Object>> weekMapList = new ArrayList<>();
            Item item = itemDao.findByIdAndStatus(itemId, Status.OK);
            if(item == null){
                throw new UtilException("item not exits");
            }
            String allWeek = item.getOpenTime();
            String [] arrayOfWeeks = allWeek.split(",");

            for(int i = 0;i < arrayOfWeeks.length;i++){
                int week = Integer.parseInt(arrayOfWeeks[i]);
                Map<String, Object> weeks = new HashMap<>();
                weeks.put("week","第"+week+"周");
                List<Map<String, Object>> datesMapList = new ArrayList<>();

                List<Batch> batchesOfWeek = batchDao.findByItemAndWeek(item,week);

                Map<String, Object> date = new TreeMap<>();
                for(int j = 0;j < batchesOfWeek.size();j++){

                    Batch batch = batchesOfWeek.get(j);
                    Date startTime = batch.getStartTime();
                    Calendar c = Calendar.getInstance();
                    c.setTime(startTime);
                    int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
                    dayOfWeek--;
                    if(dayOfWeek == 0){
                        dayOfWeek = 7;
                    }
                    String dateTime =  dayOfWeek + " " + String.valueOf(c.get(Calendar.YEAR)) + "-" + String.valueOf(c.get(Calendar.MONTH)+1)
                            + "-" + String.valueOf(c.get(Calendar.DAY_OF_MONTH)) +" ";
                    Map<String, Object> batchMap = new HashMap<>();
                    batchMap.put("id",batch.getId());
                    //这里如果不toString(),返回结果就会有8个小时的时差，暂时不知道为什么
                    batchMap.put("startTime",batch.getStartTime().toString());
                    batchMap.put("endTime",batch.getEndTime().toString());
                    batchMap.put("allowNumber",batch.getAllowNumber());
                    batchMap.put("bookNum", batch.getBooks().size());
                    batchMap.put("status", batch.getStatus());
                    batchMap.put("publish", batch.getPublish());
                    Book book = bookDao.findByUserAndBatch(getUser(),batch);
                    if(book == null){
                        batchMap.put("isBook", false);
                    }
                    else {
                        batchMap.put("isBook", true);
                    }
                    if(date.containsKey(dateTime)){
                        List<Map> dateOfBatches = (List)date.get(dateTime);
                        dateOfBatches.add(batchMap);
                        date.put(dateTime,dateOfBatches);
                    }
                    else{
                        List<Map> dateOfBatches = new ArrayList<>();
                        dateOfBatches.add(batchMap);
                        date.put(dateTime,dateOfBatches);
                    }
                }

                Iterator<Map.Entry<String, Object>> entries = date.entrySet().iterator();

                while (entries.hasNext()) {

                    Map.Entry<String, Object> entry = entries.next();

                    Map<String,Object> dates= new HashMap<>();

                    dates.put("date",entry.getKey());

                    dates.put("batches",entry.getValue());

                    datesMapList.add(dates);
                }
                weeks.put("dates", datesMapList);
                weekMapList.add(weeks);
            }


        //}

        Pager pager = new Pager(size, page, weekMapList.size(), "weeks", weekMapList);

        return success(Pager.TAG, pager.map());
    }

    @RequestMapping(value = "/page2")
    public Map<String, ?> page2(
            @RequestParam(required = false, defaultValue = "0") Integer page,
            @RequestParam(required = false, defaultValue = "20") Integer size,
            @RequestParam(required = true) Long itemId,
            @RequestParam(required = false, defaultValue = "ASC") Sort.Direction direction
    ) {
        Item item = itemDao.findByIdAndStatus(itemId, Status.OK);
        if(item == null){
            throw new UtilException("item not exits");
        }
        Pageable pageable = new PageRequest(page, size);

        Page<Batch> batches = batchDao.findAll(((root, query, cb) -> {
            Predicate predicate = cb.notEqual(root.get(Batch_.status), Status.DELETED);
            predicate = cb.and(predicate, cb.equal(root.get(Batch_.item), item));
            return predicate;
        }), pageable);

        List<Map<String, Object>> batchMapList = batches.getContent()
                .stream()
                .map(batch -> {
                    Map<String, Object> batchMap = batch.map();
                    batchMap.put("bookNum", batch.getBooks().size());
                    Book book = bookDao.findByUserAndBatch(getUser(),batch);
                    if(book == null){
                        batchMap.put("isBook", false);
                    }
                    else {
                        batchMap.put("isBook", true);
                    }
                    return batchMap;
                })
                .collect(Collectors.toList());
        Pager pager = new Pager(size, page, batches.getTotalElements(), Book.TAGS, batchMapList);

        return success(Pager.TAG, pager.map());
    }

    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value =  "/books") //此接口暂时不要用
    public Map<String,?> getBooks(
                                  @RequestParam(required = true) Long teacherId,
                                  @RequestParam(required = true) Long courseId){

        User teacher = userDao.findById(teacherId);
        if(teacher == null){
            throw  new UtilException("teacher not exits");
        }
        Course course = courseDao.findOne(courseId);
        if(course == null){
            throw new UtilException("course not exits");
        }
        if(!courseService.checkAuth(getUser(), course)){
            throw new UtilException("you have no authorization to operate other teacher's course");
        }
        List<Book> teacherBooks = new ArrayList<>();
        CourseTeacher courseTeacher = courseService.getCourseTeacherByCourseAndTeacher(course,teacher);



        return success(Book.TAGS, teacherBooks);

    }
}
