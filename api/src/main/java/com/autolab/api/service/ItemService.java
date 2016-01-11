package com.autolab.api.service;

import com.autolab.api.exception.UtilException;
import com.autolab.api.form.Time;
import com.autolab.api.model.Batch;
import com.autolab.api.model.Item;
import com.autolab.api.repository.BatchDao;
import com.autolab.api.repository.ItemDao;
import org.apache.log4j.Logger;
import org.dom4j.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by KUN on 2015/10/30.
 */

@Service
public class ItemService {
    private static Logger logger = Logger.getLogger(ItemService.class);

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private BatchDao batchDao;

    @Value("${autolab.dayOfWeek}")
    private Integer dayOfWeek;

    @Value("${autolab.startYearOfTerm}")
    private Integer startYearOfTerm;

    @Value("${autolab.startMonthOfTerm}")
    private Integer startMonthOfTerm;

    @Value("${autolab.startDayOfTerm}")
    private Integer startDayOfTerm;

    @Value("${autolab.startHourOfDay}")
    private Integer startHourOfDay;

    @Value("${autolab.numberOfSlot}")
    private Integer numberOfSlot;

    @Value("${autolab.slot}")
    private Integer slot;

    @Transactional(rollbackOn = UtilException.class)
    public void createItem(Item item, Integer allowNumber){

        if(dayOfWeek == null){
            throw new UtilException("do not define dayOfWeek");
        }
        String [] weeksOfString = item.getOpenTime().split(",");
        Integer [] weeks = new Integer[weeksOfString.length];
        for(int i = 0;i < weeksOfString.length;i++){
            weeks[i] = Integer.parseInt(weeksOfString[i]);
        }
        List<Batch> batches = new ArrayList<>();

        for(int i =0;i < weeks.length;i++){
            for(int j = 1;j <= dayOfWeek;j++){
                for(int k = 0;k < numberOfSlot;k++){
                    Batch batch = new Batch();
                    batch.setItem(item);
                    batch.setWeek(weeks[i]);
                    batch.setAllowNumber(allowNumber);
                    batch.setStartTime(new Date());
                    batch.setEndTime(new Date());
                    batches.add(batch);

                    Calendar calendar = new GregorianCalendar(startYearOfTerm,startMonthOfTerm - 1,startDayOfTerm,startHourOfDay,0);
                    calendar.add(Calendar.WEEK_OF_MONTH,weeks[i]-1);
                    calendar.add(Calendar.DAY_OF_MONTH,j-1);
                    calendar.add(Calendar.HOUR_OF_DAY,k*slot);
                    Date startTime = calendar.getTime();
                    calendar.add(Calendar.HOUR_OF_DAY,slot);
                    Date endTime = calendar.getTime();
                    batch.setStartTime(startTime);
                    batch.setEndTime(endTime);
                    batches.add(batch);

                }

            }

        }
        itemDao.save(item);
        batchDao.save(batches);
    }

    @Transactional(rollbackOn = UtilException.class)
    public void createItem2(Item item, Integer allowNumber, List<Time> times){

        List<Batch> batches = new ArrayList<>();

        for(int i = 0;i < times.size();i++){
            Batch batch = new Batch();
            batch.setItem(item);
            batch.setAllowNumber(allowNumber);
            batch.setStartTime(times.get(i).getStartTime());
            batch.setEndTime(times.get(i).getEndTime());
            batch.setWeek(0);
            batches.add(batch);
        }
        itemDao.save(item);
        batchDao.save(batches);

    }
}
