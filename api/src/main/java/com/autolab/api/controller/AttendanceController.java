package com.autolab.api.controller;

import com.autolab.api.exception.UtilException;
import com.autolab.api.model.*;
import com.autolab.api.repository.BatchDao;
import com.autolab.api.repository.BookDao;
import com.autolab.api.service.AttendanceService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by KUN on 2015/11/8.
 */

@RestController
@RequestMapping("/attendance")
public class AttendanceController extends BaseController {
    public static final Logger logger = LoggerFactory.getLogger(AttendanceController.class);

    @Autowired
    protected BatchDao batchDao;

    @Autowired
    protected AttendanceService attendanceService;

    @RequestMapping("/upload")
    public Map<String,?> uploadExcel(@RequestParam("file") MultipartFile file,
                                     @RequestParam(required = true) Long batchId){

        Batch batch = batchDao.findByIdAndStatus(batchId, Status.OK);
        if(batch == null){
            throw new UtilException("batch not exits");
        }

        Integer attendacneCount = attendanceService.setAttendance(file, batch);
        return success("attendanceCount",attendacneCount);
    }
}
