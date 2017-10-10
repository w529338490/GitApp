package com.wchstrife.dao;

import com.wchstrife.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wangchenghao on 2017/7/31.
 */
@Repository
public interface UserDao extends JpaRepository<User, String>{

    @Query("from User u where u.username = :username and password = :password")
    User findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);

    @Query("from User u where u.username=:username")
    List<User> findByUsernameByName(@Param("username") String username);

}
