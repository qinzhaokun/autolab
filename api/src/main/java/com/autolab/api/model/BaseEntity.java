package com.autolab.api.model;

import com.autolab.api.util.AppContextManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Map;

/**
 * Created by zhao on 15/10/22.
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    public static final String PREFIX = "autolab_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    protected Long id;


    //状态
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    protected Status status = Status.OK;


    //创建时间
    @Getter
    @Setter
    protected Date createTime = new Date();


    @Transient
    protected Map<String, Object> map;

    /**
     * 添加一些@JsonIgnore的字段。
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> map() {

        if (map == null) {


            //Spring MVC的框架中已经装填了ObjectMapper，放心大胆的去使用就好了
            ObjectMapper mapper = AppContextManager.getAppContext().getBean(ObjectMapper.class);


            map = mapper.convertValue(this, Map.class);
        }

        return map;
    }


}
