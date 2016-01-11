package com.autolab.api.controller;

import com.autolab.api.exception.UtilException;
import com.autolab.api.model.Book;
import com.autolab.api.model.User;
import com.autolab.api.repository.BookDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by ABC on 2015/10/22 0022.
 */

@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    protected BookDao bookDao;

    @PreAuthorize(User.Role.HAS_ROLE_USER)
    @RequestMapping(value = "/grade")
    public Map<String, ?> getGrades() {

        User user=getUser();

        List<Book> books = bookDao.findByUser(user);

        return success(Book.TAGS,books);
    }

    /**
     *
     * @param role
     * @return
     */
    @PreAuthorize(User.Role.HAS_ROLE_ADMIN)
    @RequestMapping(value = "/{role}")
    public Map<String, ?> getUser(@PathVariable String role){
        List<User> users;
        if(role.equals("ROLE_TEACHER")){
            users = userDao.findByRole(User.Role.ROLE_TEACHER);
        }
        else if(role.equals("ROLE_USER")){
            users = userDao.findByRole(User.Role.ROLE_USER);
        }
        else if (role.equals("ROLE_ADMIN")){
            users = userDao.findByRole(User.Role.ROLE_ADMIN);
        }
        else{
            throw new UtilException("role is invalid");
        }
        return success(User.TAGS,users);
    }

}
