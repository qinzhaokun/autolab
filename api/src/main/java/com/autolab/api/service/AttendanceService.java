package com.autolab.api.service;

import com.autolab.api.exception.UtilException;
import com.autolab.api.model.Attendance;
import com.autolab.api.model.Batch;
import com.autolab.api.model.Book;
import com.autolab.api.model.User;
import com.autolab.api.repository.BookDao;
import com.autolab.api.repository.UserDao;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by KUN on 2015/11/8.
 */

@Service
public class AttendanceService {
    private static Logger logger = Logger.getLogger(AttendanceService.class);

    @Autowired
    protected BookDao bookDao;

    @Autowired
    protected UserDao userDao;

    @Transactional(rollbackOn = UtilException.class)
    public Integer setAttendance(MultipartFile file, Batch batch){
        int attendacneCount = 0;
        try {

            //Workbook wb = new XSSFWorkbook(file.getInputStream());
            Workbook wb = new HSSFWorkbook(file.getInputStream());
            Sheet sheet = wb.getSheetAt(0);
            for( int i = 1; i <= sheet.getLastRowNum(); i++ ){
                Row row = sheet.getRow(i);

                row.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                String jaccountId  = row.getCell(0).getStringCellValue();
                row.getCell(1).setCellType(Cell.CELL_TYPE_STRING);
                String attendTime = row.getCell(1).getStringCellValue();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date;
                try
                {
                    date = sdf.parse(attendTime);
                }catch (ParseException e)
                {
                    throw new UtilException("invalid date format");
                }

                User user = userDao.findByJaccountId(jaccountId);

                if(user == null){
                    logger.debug("invalid user");
                    continue;
                }
                Book book = bookDao.findByUserAndBatch(user,batch);
                if(book != null){
                    book.setAttendance(Attendance.YES);
                    book.setAttendTime(date);
                    bookDao.save(book);
                    attendacneCount++;
                }
            }
        } catch (IOException e) {
            throw new UtilException("invalid file");
        }

        return attendacneCount;
    }
}
