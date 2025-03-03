package com.safedrive.mapper;

import com.safedrive.domain.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    
    @Select("SELECT * FROM users")
    List<User> findAll();
    
    @Select("SELECT * FROM users WHERE id = #{id}")
    User findById(@Param("id") Long id);
    
    @Insert("INSERT INTO users (username, email, password, created_at, updated_at) " +
            "VALUES (#{username}, #{email}, #{password}, NOW(), NOW())")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(User user);
    
    @Update("UPDATE users SET username = #{username}, email = #{email}, " +
            "password = #{password}, updated_at = NOW() WHERE id = #{id}")
    int update(User user);
    
    @Delete("DELETE FROM users WHERE id = #{id}")
    int delete(@Param("id") Long id);

    // XML 매퍼 메서드
    List<User> findAllXml();
    User findByIdXml(Long id);
    int insertXml(User user);
    int updateXml(User user);
    int deleteXml(Long id);
} 