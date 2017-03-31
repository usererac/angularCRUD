package com.in50us.springboot.service;


import com.in50us.springboot.model.User;

import java.util.List;

public interface UserService {

    User findById(Long id);

    User findByName(String name);

    void saveUser(User user);

    void updateUser(User user);

    void deletUserById(Long id);

    void deleteAllUser();

    List<User> findAllUser();

    boolean isUserExist(User user);
}
