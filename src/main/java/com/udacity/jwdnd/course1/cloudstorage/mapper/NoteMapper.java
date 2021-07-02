package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
   @Select("SELECT noteId, noteTitle,noteDescription, userId FROM NOTES WHERE userid = #{userId}")
   public List<Note> listByUser(Integer userId);

    @Select("SELECT * FROM NOTES WHERE nodeid = #{noteId} AND userid = #{userId}")
    public Note getByUser( Integer noteId, Integer userId);

    @Select("SELECT COUNT(1) FROM NOTES WHERE notetitle = #{noteTitle} AND noteid <> NVL(#{noteId}, -1) AND userid = #{userId}")
    public Boolean existsByTitleWithAnotherId(String noteTitle,Integer noteId, Integer userId);

    @Insert("INSERT INTO NOTES(notetitle,notedescription,userid) VALUES(#{noteTitle},#{noteDescription},#{userId})")
    @Options(useGeneratedKeys = true,keyProperty ="noteId")
    public int addNote(Note note);

    @Delete("delete from notes where noteid = #{noteId} and userid = #{userId}")
    public void deleteNote(Integer noteId,Integer userId);

    @Update("UPDATE NOTES SET notetitle = #{noteTitle}, notedescription = #{noteDescription} where userid = #{userId} AND noteid = #{noteId}")
    public int updateNote(Note note);

}
