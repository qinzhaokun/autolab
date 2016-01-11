package com.autolab.api.controller;

import com.autolab.api.exception.UtilException;
import com.autolab.api.form.BookForm;
import com.autolab.api.model.*;
import com.autolab.api.repository.BatchDao;
import com.autolab.api.repository.BookDao;
import net.sf.jasperreports.olap.mapping.Mapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Predicate;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by KUN on 2015/10/24.
 */

@RestController
@RequestMapping("/book")
public class BookController extends BaseController {
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    protected BookDao bookDao;

    @Autowired
    protected BatchDao batchDao;

    /**
     * create a book
     * @param  form
     * @return
     */

    @PreAuthorize(User.Role.HAS_ROLE_USER)
    @RequestMapping(value = "/create")
    public Map<String,?> create(@Valid BookForm form){
        Book book=form.generateBook();
        book.setUser(getUser());

        Batch batch=book.getBatch();

        List<Book> books = getUser().getBooks();

        for(int i = 0;i < books.size();i++){
            if(batch.getItem().equals(books.get(i).getBatch().getItem())){
                throw new UtilException("you have booked this item");
            }
        }

        if(  batch.getBooks().size()>= batch.getAllowNumber()){
            throw new UtilException("already reaches the max allow number ");
        }

        bookDao.save(book);

        return success(Book.TAG, book);
    }


    /**
     * detele a book
     * @param bookId
     * @return
     */
    @PreAuthorize(User.Role.HAS_ROLE_USER)
    @RequestMapping(value ="/del/{bookId}")
    public  Map<String,?> del(@PathVariable Long bookId){
        Book book = bookDao.findOne(bookId);

        if(book == null){
            throw new UtilException("book is not exit");
        }

        if( !getUser().equals(book.getUser()) ){
            throw new  UtilException("cannot delete other students book");
        }

        bookDao.delete(book);

        return success(Book.TAG,bookId);
    }


    /**
     * student find books
     * @return
     */
    @PreAuthorize(User.Role.HAS_ROLE_USER)
    @RequestMapping(value ="/page")
    public  Map<String,?> find(
                                @RequestParam(required = false, defaultValue = "0") Integer page,
                               @RequestParam(required = false, defaultValue = "20") Integer size){

        Pageable pageable = new PageRequest(page, size);

        Page<Book> books = bookDao.findAll(((root, query, cb) -> {
            Predicate predicate = cb.notEqual(root.get(Book_.status), Status.DELETED);
            predicate = cb.and(predicate, cb.equal(root.get(Book_.user), getUser()));
            return predicate;
        }) , pageable);


        List<Map<String, Object>> bookMapList = books.getContent()
                .stream()
                .map(book -> {
                    Map<String, Object> bookMap = new HashMap();
                    bookMap.put("id", book.getId());
                    bookMap.put("user", book.getUser());
                    bookMap.put("batch", book.getBatch());
//                    if ((book.getBatch().getPublish() == Publish.NO || book.getBatch().getPublish() == null) && book.getGrade() != null) {
//                        bookMap.put("grade", null);
//                    } else {
                        bookMap.put("grade", book.getGrade());
//                    }
                    return bookMap;
                })
                .collect(Collectors.toList());

        Pager pager = new Pager(size, page, books.getTotalElements(), Book.TAGS, bookMapList);

        return success(Pager.TAG, pager.map());
    }









}
