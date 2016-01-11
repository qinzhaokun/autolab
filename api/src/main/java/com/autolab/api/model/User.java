package com.autolab.api.model;

import com.autolab.api.util.AppContextManager;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.*;

/**
 * Created by zhao on 15/10/22.
 */
@EqualsAndHashCode(callSuper = false)
@Data
@Entity
@Table(name = BaseEntity.PREFIX + "user")
public class User extends BaseEntity implements UserDetails, CredentialsContainer {

    private static final long serialVersionUID = 100L;
    public static String TAG = User.class.getSimpleName().toLowerCase();
    public static String TAGS = TAG + "s";


    //和jaccount相关的字段
    private String jaccountUid;
    private String jaccountChinesename;
    private String jaccountId;
    private String jaccountStudent;
    private String jaccountDept;

    //用户角色
    @Enumerated(EnumType.STRING)
    private Role role;

    private String username;

    @JsonIgnore
    private String password;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = {}, fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "teacher", cascade = {}, fetch = FetchType.LAZY)
    private List<CourseTeacher> courseTeachers = new ArrayList<>();


    @JsonIgnore
    @OneToMany(mappedBy = "student", cascade = {}, fetch = FetchType.LAZY)
    private List<CourseTeacherStudent> courseTeacherStudents = new ArrayList<>();






    /**
     * 产生一个新的jaccount
     */
    public static User generateJaccountUser(String jaccountUid, String jaccountChinesename, String jaccountId,String jaccountStudent,String jaccountDept) {
        User user = new User();
        user.setJaccountUid(jaccountUid);
        user.setJaccountChinesename(jaccountChinesename);
        user.setJaccountId(jaccountId);
        user.setJaccountStudent(jaccountStudent);
        user.setJaccountDept(jaccountDept);
        if (jaccountStudent.equals("yes")){
            user.setRole(User.Role.ROLE_USER);
        }else {
            user.setRole(User.Role.ROLE_ADMIN);
        }
        user.setStatus(Status.OK);

        ApplicationContext appContextManager = AppContextManager.getAppContext();
        PasswordEncoder passwordEncoder =appContextManager.getBean(PasswordEncoder.class);
        user.setUsername(jaccountId);
        user.setPassword(passwordEncoder.encode(jaccountId));

        return user;
    }

    public User() {

    }

    /**
     * 复制构造函数
     */
    public User(User user) {
        id = user.getId();
        jaccountUid = user.getJaccountUid();
        jaccountChinesename = user.getJaccountChinesename();
        jaccountId = user.getJaccountId();
        jaccountStudent = user.getJaccountStudent();
        jaccountDept = user.getJaccountDept();

        role = user.getRole();
        status = user.getStatus();
        createTime = user.getCreateTime();
        username = user.getUsername();
        password = user.getPassword();
    }

    /**********************************interface UserDetails Start*************************************************/
    /**
     * 用户所拥有的权限，主要是供UserRepositoryUserDetails使用
     *
     * @return Collection
     */
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> roles = new HashSet<>();

        if (role == Role.ROLE_USER) {
            roles.add(new SimpleGrantedAuthority(Role.ROLE_USER.getName()));
        } else if (role == Role.ROLE_ADMIN) {
            roles.add(new SimpleGrantedAuthority(Role.ROLE_USER.getName()));
            roles.add(new SimpleGrantedAuthority(Role.ROLE_ADMIN.getName()));
        }

        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    /**********************************interface UserDetails End*************************************************/


    /******************************************************
     * CredentialsContainer
     *******************************************************/
    public void eraseCredentials() {
        System.out.println("---------eraseCredentials-----------");
    }

    /*************************************************************************************************************/


    //角色名。特别注意，此处必须是ROLE_开头，不然Oauth就要出错。
    public enum Role {
        ROLE_USER("ROLE_USER"),
        ROLE_ADMIN("ROLE_ADMIN"),
        ROLE_TEACHER("ROLE_TEACHER");

        public static final String HAS_ROLE_USER = "hasRole('ROLE_USER')";
        public static final String HAS_ROLE_ADMIN = "hasRole('ROLE_ADMIN')";
        public static final String HAS_ROLE_TEACHER = "hasRole('ROLE_TEACHER')";

        @Getter
        private String name;

        Role(String name) {
            this.name = name;
        }
    }

}
