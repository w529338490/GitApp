package com.wchstrife.service;

import com.wchstrife.dao.UserDao;
import com.wchstrife.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by wangchenghao on 2017/8/2.
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    /**
     * 用户登录
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password){
        User user = userDao.findByUsernameAndPassword(username, password);
        if(null == user){

            return false;
        }else {

            return true;
        }
    }

    /**
     * findUserByName
     */
    public  boolean findUserByName(String username){

        if(userDao.findByUsernameByName(username)!=null &&userDao.findByUsernameByName(username).size()>0){

            return true;
        }
        return false;
    }

    /**
     * 注册
     *
     */
    public  boolean regiset(User user){

        if(user!=null){
            userDao.save(user);
            return true;
        }

        return false;
    }
}
