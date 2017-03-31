package com.in50us.springboot.service;


import com.in50us.springboot.model.User;
import com.in50us.springboot.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        saveUser(user);
    }

    @Override
    public void deletUserById(Long id) {
        userRepository.delete(id);
    }

    @Override
    public void deleteAllUser() {
        userRepository.deleteAll();
    }

    @Override
    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    @Override
    public boolean isUserExist(User user) {
        return findByName(user.getName()) != null;
    }
}
