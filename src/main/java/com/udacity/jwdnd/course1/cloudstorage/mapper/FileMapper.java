package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Results(id = "messageResultMap", value = {
            @Result(property="fileId",column="fileId"),
            @Result(property="fileName",column="filename"),
            @Result(property="userid",column="userid")
    })
    @Select("SELECT fileId, filename, userid FROM FILES WHERE userid = #{userId}")
    public List<File> listFileNames(Integer userId);

    @Select("select * from files where fileId = #{fileId}")
    public File findFileById(Integer fileId);

    @Select("SELECT * FROM files where fileId = #{fileId} AND userid = #{userId}")
    public File getByUser(Integer fileId, Integer userId);

    @Select("SELECT COUNT(1) FROM FILES WHERE fileName = #{fileName} and userid = #{userId}")
    public Boolean checkFileExitsByName(String fileName, Integer userId);

    @Insert("INSERT INTO FILES(filename,contenttype,filesize,userid,filedata)VALUES(#{fileName},#{contentType},#{fileSize},#{userid},#{fileData})")
    @Options(useGeneratedKeys = true, keyProperty ="fileId")
    public int addFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    public void deleteByFileId(Integer fileId);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId} AND userid = #{userId}")
    public void deleteByUser(Integer fileId,Integer userId);



}
