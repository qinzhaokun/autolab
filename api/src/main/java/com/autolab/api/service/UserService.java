package com.autolab.api.service;

import com.autolab.api.model.OAuth2;
import com.autolab.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.stereotype.Service;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;

import java.io.Serializable;
import java.util.*;
import org.apache.log4j.Logger;

/**
 * Created by ABC on 2015/10/22 0022.
 */

@Service
public class UserService {

    private static Logger logger = Logger.getLogger(UserService.class);


    /*******************************Auth ���*************************************/
}
