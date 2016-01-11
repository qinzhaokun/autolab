package com.autolab.api.form;

import com.autolab.api.exception.UtilException;
import com.autolab.api.model.Batch;
import com.autolab.api.model.Book;
import com.autolab.api.model.User;
import com.autolab.api.repository.BatchDao;
import com.autolab.api.repository.UserDao;
import com.autolab.api.util.AppContextManager;
import lombok.Data;
import org.springframework.context.ApplicationContext;

import javax.validation.constraints.NotNull;

/**
 * Created by ting on 2015/10/25.
 */
@Data
public class BookForm {
    private Long id;

    @NotNull
    private Long batchId;

    private Long grade;

    public Book generateBook(){
        Book  book = new Book();

        if(grade != null){
            throw new UtilException("Student cannot set the grade");
        }

        ApplicationContext applicationContext = AppContextManager.getAppContext();
        BatchDao batchDao = applicationContext.getBean(BatchDao.class);
        Batch batch = batchDao.findOne(batchId);
        if(batch == null){
            throw new UtilException("batch is not exit");
        }
        book.setBatch(batch);

        return book;
    }

    public void updateBook(Book book){
        if(batchId != null){
            ApplicationContext applicationContext = AppContextManager.getAppContext();
            BatchDao batchDao = applicationContext.getBean(BatchDao.class);
            Batch batch = batchDao.findOne(batchId);
            if(batch == null){
                throw new UtilException("batch is not exit");
            }
            book.setBatch(batch);
        }


        if(grade != null){
            throw new UtilException("Student cannot set the grade");
        }

    }

}
