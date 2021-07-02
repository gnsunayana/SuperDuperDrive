package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.model.UserCredentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserCredentialsMapper {

    @Select("select * from CREDENTIALS where credentialid = #{credentialId} AND userid = #{userId}")
    public UserCredentials getByUser(Integer credentialId,Integer userId);

    @Select("SELECT credentialid,url,username,key,password,userid FROM CREDENTIALS WHERE userid = #{userId}")
    public List<UserCredentials> listByUser(Integer userId);

    @Insert("INSERT INTO CREDENTIALS(url,username,key,password,userid)VALUES (#{url},#{userName},#{key},#{password},#{userId})")
    @Options(useGeneratedKeys = true, keyProperty ="credentialId")
    public int addUserCredentials(UserCredentials userCredentials);

    @Delete("DELETE from CREDENTIALS where credentialid = #{credentialId} AND userid = #{userId}")
    public void deleteByUser(Integer credentialId,Integer userId);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{userName}, key= #{key}, password = #{password}, userid = #{userId} where credentialid = #{credentialId} AND userid = #{userId}")
    public int updateByUser(UserCredentials userCredentials);

    @Select("SELECT COUNT(1) FROM CREDENTIALS WHERE url = #{url} AND username = #{userName} AND credentialid <> NVL(#{credentialId},-1) AND userid = #{userId}")
    public boolean existsByUrlUserAnotherId(String url,String userName, Integer credentialId,Integer userId);
}


