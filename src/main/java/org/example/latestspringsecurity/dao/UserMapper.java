package org.example.latestspringsecurity.dao;

import org.apache.ibatis.annotations.Param;
import org.example.latestspringsecurity.model.User;

public interface UserMapper {

    User findByUserName(@Param("username") String username);

}
