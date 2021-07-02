package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Select("select * from users where userid = #{userId}")
    public User findByUserId(Integer userId);

    @Select("select * from users where username = #{userName}")
    public User findByUserName(String userName);

 /*   @Select("select * from user")
    public List<User> getAllUsers();*/

    @Insert("INSERT INTO USERS(username,salt,password,firstname,lastname)VALUES(#{userName},#{salt},#{password},#{firstName},#{lastName})")
    @Options(useGeneratedKeys=true,keyProperty = "userId")
    int addUser(User user);

    /*@Delete("DELETE from USER where userId = #{userId}")
    void deleteUser(Integer userId);

    @Update("UPDATE USER SET userName = #{userName}, salt = #{salt}, password = #{password}, firstName = #{firstName}, lastName = #{lastName} where userId = #{userId}")
    void updateUser(User user);
*/

}




