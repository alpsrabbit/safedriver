package com.safedrive.service;

import com.safedrive.domain.User;
import com.safedrive.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserMapper userMapper;
    
    public List<User> findAll() {
        return userMapper.findAll();
    }
    
    public User findById(Long id) {
        return userMapper.findById(id);
    }
    
    @Transactional
    public User create(User user) {
        userMapper.insert(user);
        return user;
    }
    
    @Transactional
    public User update(User user) {
        userMapper.update(user);
        return user;
    }
    
    @Transactional
    public void delete(Long id) {
        userMapper.delete(id);
    }
    
    // XML 매퍼 사용 예제
    public List<User> findAllXml() {
        return userMapper.findAllXml();
    }
} 