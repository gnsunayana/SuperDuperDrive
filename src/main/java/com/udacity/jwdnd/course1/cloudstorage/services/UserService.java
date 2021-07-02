package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService){
        this.userMapper= userMapper;
        this.hashService= hashService;
    }

    public int createUser(User user){
        SecureRandom random= new SecureRandom();
        byte[] salt= new byte[16];
        random.nextBytes(salt);
        String encodedSalt= Base64.getEncoder().encodeToString(salt);
        String hashedPassword= hashService.getHashedValue(user.getPassword(),encodedSalt);
        User newUser= new User(null,user.getUserName(),encodedSalt,hashedPassword,user.getFirstName(),user.getLastName());
        return userMapper.addUser(newUser);

    }


    public User getUser(String userName){
        User user = userMapper.findByUserName(userName);
        System.out.println("The userid is:"+user.getUserId());
        return user;
    }

    public boolean isUsernameAvailable(String username){
        return userMapper.findByUserName(username) == null;
    }
}
