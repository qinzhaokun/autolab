package com.autolab.api.service;

import com.autolab.api.exception.UtilException;
import com.autolab.api.model.Batch;
import com.autolab.api.model.Book;
import com.autolab.api.model.User;
import com.autolab.api.repository.BatchDao;
import com.autolab.api.repository.BookDao;
import com.autolab.api.repository.UserDao;
import org.apache.log4j.Logger;
import org.dom4j.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Created by KUN on 2015/10/31.
 */

@Service
public class BatchService {

    private static Logger logger = Logger.getLogger(BatchService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private BookDao bookDao;

    @Autowired
    private BatchDao batchDao;

    @Transactional(rollbackOn = UtilException.class)
    public void setGrade(Long [] studentIds, String []grades, Long batchId){
        Batch batch = batchDao.findOne(batchId);
        if(batch == null){
            throw new UtilException("batch not exits");
        }
        if(studentIds.length != grades.length){
            throw new UtilException("the lengths do not match!");
        }
        for(int i = 0;i < studentIds.length;i++){
            User student = userDao.findOne(studentIds[i]);
            if(student == null){
                throw new UtilException("student not exit");
            }
            Book book = bookDao.findByUserAndBatch(student,batch);
            book.setGrade(grades[i]);
            bookDao.save(book);
        }
    }

    @Transactional(rollbackOn = UtilException.class)
    public void setGrade2(String []grades, Long []bookIds){

        if(bookIds.length != grades.length){
            throw new UtilException("the lengths do not match!");
        }
        for(int i = 0;i < bookIds.length;i++){
            Book book = bookDao.findOne(bookIds[i]);
            book.setGrade(grades[i]);
            bookDao.save(book);
        }
    }
}
