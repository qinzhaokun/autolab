package com.autolab.api.controller;

import com.autolab.api.ApiConfig;
import com.autolab.api.exception.UtilException;
import com.autolab.api.model.BaseEntity_;
import com.autolab.api.model.Pager;
import com.autolab.api.model.Status;
import com.autolab.api.model.User;
import com.autolab.api.repository.UserDao;
import com.autolab.api.util.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhao on 15/10/23.
 */
public abstract class BaseController {



    @Autowired
    protected UserDao userDao;

    @Autowired
    protected ApiConfig apiConfig;

    /**
     * 判断当前请求的用户是否已经登录
     */
    protected boolean isLogin() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            Method getId = principal.getClass().getMethod("getId");
            Long userId = (Long) getId.invoke(principal);
            if (userId != null) {

                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
        }

    }

    /**
     * 获取当前用户的id.
     *
     * @return 当前用户的id.
     */
    protected Long getUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        try {
            Method getId = principal.getClass().getMethod("getId");
            Long userId = (Long) getId.invoke(principal);
            if (userId != null) {
                return userId;
            } else {
                throw new UtilException("请重新登录");
            }

        } catch (Exception e) {
            throw new UtilException("请重新登录");
        }
    }

    /**
     * 获取当前的登录的这个user.
     *
     * @return User.
     */
    protected User getUser() {
        return userDao.findOne(getUserId());
    }

    protected Map<String, Object> error() {
        return new HashMap<String, Object>() {{
            put(Status.TAG, Status.ERROR);
        }};
    }

    protected Map<String, Object> error(String message) {
        Map<String, Object> model = error();
        model.put("message", message);
        return model;
    }

    protected Map<String, Object> success() {
        return new HashMap<String, Object>() {{
            put(Status.TAG, Status.OK);
        }};
    }

    protected Map<String, Object> success(String name, Object data) {
        Map<String, Object> model = success();
        model.put(name, data);
        return model;
    }

    protected <T> Map<String, Object> success(String name, Page<?> page, Pageable pageable) {

        Pager pager = new Pager(
                pageable.getPageSize(),
                pageable.getPageNumber(),
                page.getTotalElements(),
                name,
                page.getContent());

        return success(Pager.TAG,pager.map());
    }


    /**
     * 检查参数是否为空
     *
     * @param list the list
     * @return true, if successful
     */
    public boolean checkParams(Object... list) {
        return Validation.checkParam(list);
    }

    /**
     * 查询字段的状态不为DELETED
     */
    @SuppressWarnings("unchecked")
    protected Predicate setStatusNotDeleted(Root root, CriteriaBuilder cb) {
        return cb.notEqual(root.get(BaseEntity_.status), Status.DELETED);
    }

}
