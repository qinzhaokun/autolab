package com.autolab.api.form;

import com.autolab.api.exception.UtilException;
import com.autolab.api.model.Batch;
import com.autolab.api.model.Item;
import com.autolab.api.model.Status;
import com.autolab.api.repository.ItemDao;
import com.autolab.api.util.AppContextManager;
import com.autolab.api.util.DateUtil;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Created by ABC on 2015/10/24 0024.
 */

@Data
public class BatchForm {

    private Long id;

    @NotNull
    private Long itemId;

    @NotNull
    private Integer allowNumber;

    @NotNull
    @DateTimeFormat(pattern= DateUtil.DEFAULT_FORMAT)
    private Date startTime;

    @NotNull
    @DateTimeFormat(pattern= DateUtil.DEFAULT_FORMAT)
    private Date endTime;

    private Status status;

    public Batch generateBatch(){
        Batch batch = new Batch();
        batch.setAllowNumber(allowNumber);
        batch.setStartTime(startTime);
        batch.setEndTime(endTime);
        ApplicationContext applicationContext = AppContextManager.getAppContext();
        ItemDao itemDao = applicationContext.getBean(ItemDao.class);
        Item item = itemDao.findOne(itemId);
        if(item == null){
            throw new UtilException("item is not exit");
        }
        batch.setItem(item);
        return batch;
    }

    public void updateBatch(Batch batch){
        if(allowNumber != null){
            batch.setAllowNumber(allowNumber);
        }
        if(startTime != null){
            batch.setStartTime(startTime);
        }
        if(endTime != null){
            batch.setEndTime(endTime);
        }
        if(itemId != null){
            ApplicationContext applicationContext = AppContextManager.getAppContext();
            ItemDao itemDao = applicationContext.getBean(ItemDao.class);
            Item item = itemDao.findOne(itemId);
            if(item == null){
                throw new UtilException("item is not exit");
            }
            batch.setItem(item);
        }
        if(status != null){
            if(batch.getBooks().size() > 0){
                throw new UtilException("this batch hae been booked by students");
            }
            batch.setStatus(status);
        }
    }
}
