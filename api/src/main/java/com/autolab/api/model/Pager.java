package com.autolab.api.model;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhao on 15/10/23.
 */
public class Pager {

    public static final String TAG=Pager.class.getSimpleName().toLowerCase();


    @Getter
    private int pageSize;
    @Getter
    private int page;
    @Getter
    @Setter
    private long totalItems;
    private String keyName;
    private List<?> listData;

    public Pager(int pageSize,int page){
        this.pageSize=pageSize;
        this.page=page;
    }

    public int getStart(){
        return page*pageSize;
    }

    public void setDate(String keyNam,List<?> listData){
        this.keyName=keyNam;
        this.listData=listData;
    }

    /**
     * 手动制造一个Pager.
     */
    public Pager(int pageSize,int page,long totalItems,String keyName,List<?> listData){

        this.pageSize=pageSize;
        this.page=page;
        this.totalItems=totalItems;
        this.keyName=keyName;
        this.listData=listData;
    }


    public Map<String,Object> map(){

        return new HashMap<String,Object>(){{
            put("pageSize", pageSize);
            put("page", page);
            put("totalItems", totalItems);
            put(keyName, listData);
        }};
    }

}