package com.autolab.api.controller;

import com.autolab.api.ApiConfig;
import com.autolab.api.controller.BaseController;
import com.autolab.api.exception.UtilException;
import com.autolab.api.model.OAuth2;
import com.autolab.api.model.User;
import com.autolab.api.service.UserService;
import com.ning.http.client.AsyncHttpClient;
import com.ning.http.client.AsyncHttpClientConfig;
import com.ning.http.client.Param;
import com.ning.http.client.Response;
import edu.sjtu.jaccount.JAccountManager;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Hashtable;


import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by zhao on 15/10/22.
 */
@Controller
public class HomeController extends  BaseController{
    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    ApiConfig config;

    @Getter
    @Value("${autolab.debug}")
    private boolean debug;

    //通过jaccount参数换取授权access_token
    public OAuth2 getOAuth2(String url,List<Param> params) {

        AsyncHttpClientConfig.Builder builder = new AsyncHttpClientConfig.Builder();
        //设置5秒钟的链接超时。
        builder.setConnectTimeout(5000).build();

        AsyncHttpClient client = new AsyncHttpClient(builder.build());

        //装填请求参数
        Future<Response> future = client.preparePost(url).setQueryParams(params).execute();

        try {
            Response response = future.get();
            //这里必须要去关掉这个http的连接。
            client.closeAsynchronously();
            try {
                String str = response.getResponseBody("UTF-8");
                logger.debug(str);

                //返回的格式：
                OAuth2 oAuth2 = OAuth2.fromJson(str, OAuth2.class);
                if (oAuth2 == null) {
                    logger.debug("oauth2 null!");
                    throw new UtilException("oauth2 null!");
                } else {
                    if (oAuth2.getErrcode() == null && oAuth2.getErrmsg() == null) {


                        return oAuth2;
                    } else {

                        logger.debug(oAuth2.getErrcode() + ":" + oAuth2.getErrmsg());

                        throw new UtilException(oAuth2.getErrcode() + ":" + oAuth2.getErrmsg());
                    }
                }
            } catch (Exception e) {
                logger.debug("GetResponseBody error when getting the WebAuthEntity!");

                throw new UtilException("GetResponseBody error when getting the WebAuthEntity!");
            }
        } catch (InterruptedException | ExecutionException e) {
            //这里必须要去关掉这个http的连接。
            client.closeAsynchronously();
            logger.debug("Get the WebAuthEntity error!");
            throw new UtilException("Get the WebAuthEntity error!");
        }
    }

    @RequestMapping("/home")
    public String home(Model model,HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        String sid = "jaexperimentreservation20150922";
        String keyDir;
        if (debug){
            keyDir = this.getClass().getResource("/public/static").getPath();
            //找到路径的开头 /
            int index = keyDir.indexOf("/");
            keyDir = keyDir.substring(index);
        }else {
            keyDir = "/var/www/autolab/api/src/main/resources/public/static";
        }
        logger.debug("login"+keyDir);

        JAccountManager jam = new JAccountManager(sid, keyDir);

        Hashtable ht = jam.checkLogin(request, response, session, request.getRequestURI());

        if (ht != null) {
            logger.debug((String) ht.get("uid"));

            List<Param> params = new ArrayList<>();
            params.add(new Param("jaccount_uid", (String)ht.get("uid")));
            params.add(new Param("jaccount_chinesename",(String)ht.get("chinesename")));
            params.add(new Param("jaccount_id", (String)ht.get("id")));
            params.add(new Param("jaccount_student", (String)ht.get("student")));
            params.add(new Param("jaccount_dept", (String)ht.get("dept")));
            params.add(new Param("grant_type", "jaccount"));
            params.add(new Param("scope", "read write"));
            params.add(new Param("client_secret", "f506d105142e2928e2e37675b560ff75"));
            params.add(new Param("client_id", "clientapp"));

            String url;
            if (debug){
                url = "http://localhost:8025/oauth/token";
            }else{
                url = "http://120.26.72.17:8025/oauth/token";
            }
            OAuth2 oAuth2 = getOAuth2(url, params);

            model.addAttribute("oauth2", oAuth2);
            model.addAttribute("user_uid",(String)ht.get("uid"));
            model.addAttribute("user_id",(String)ht.get("id"));
            model.addAttribute("user_chinesename",(String)ht.get("chinesename"));
            model.addAttribute("user_student",(String)ht.get("student"));
            model.addAttribute("user_dept",(String)ht.get("dept"));

            return "static/index";

        } else {
            return "static/page/login";

        }


    }

    @RequestMapping("/loginout")
    public String loginout(Model model,HttpServletRequest request, HttpServletResponse response, HttpSession session) {

        String sid = "jaexperimentreservation20150922";
        String keyDir;
        if (debug){
            keyDir = this.getClass().getResource("/public/static").getPath();
            //找到路径的开头 /
            int index = keyDir.indexOf("/");
            keyDir = keyDir.substring(index);
        }else {
            keyDir = "/var/www/autolab/api/src/main/resources/public/static";
        }
        logger.debug("loginout:"+keyDir);

        JAccountManager jam = new JAccountManager(sid, keyDir);
        boolean loggedout = jam.logout(request, response, request.getRequestURI());
        String s = String.valueOf(loggedout);
        logger.debug("loginout :"+s);

        if (!loggedout) {
            return "static/tpl/404";
        }else {
            return "static/view/login";
        }

    }



}
