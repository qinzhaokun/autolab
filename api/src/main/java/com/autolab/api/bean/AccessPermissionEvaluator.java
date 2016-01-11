package com.autolab.api.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;

import java.io.Serializable;

/**
 *
 * Created by lishuang on 15/4/7.
 */


public class AccessPermissionEvaluator implements PermissionEvaluator {

    private static Logger logger = LoggerFactory.getLogger(AccessPermissionEvaluator.class);

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        logger.debug("-----------------authentication: " + authentication + ", target: " + targetDomainObject
                + ", permission: " + permission+"-------------------");
        if (permission instanceof String) {
            String access = (String) permission;
        }
        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
