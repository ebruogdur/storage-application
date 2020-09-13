package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface CredentialMapper {


    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userId) VALUES(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS")
    List<Credential> getAllCredentials();

    @Update("UPDATE CREDENTIALS SET url=#{url}, username =#{username},password =#{password} WHERE credentialId =#{credentialId}")
    void updateCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Credential getCredential(int credentialId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId =#{credentialId}")
    void deleteCredential(int credentialId);

}
