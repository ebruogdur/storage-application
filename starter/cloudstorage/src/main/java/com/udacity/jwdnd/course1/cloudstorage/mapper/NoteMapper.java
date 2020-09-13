package com.udacity.jwdnd.course1.cloudstorage.mapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId) VALUES(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int insertNote(Note note);


    @Update("UPDATE NOTES SET noteTitle=#{noteTitle}, noteDescription =#{noteDescription} WHERE noteId =#{noteId}")
    void updateNote(Note note);


    @Select("SELECT * FROM NOTES")
    List<Note> getAllNotes();


    @Select("SELECT * FROM NOTES WHERE noteId = #{noteId}")
    Note getNote(int noteId);

    @Delete("DELETE FROM NOTES WHERE noteId =#{noteId}")
    void deleteNote(int noteId);
}
