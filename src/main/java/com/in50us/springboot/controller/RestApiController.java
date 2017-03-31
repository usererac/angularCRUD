package com.in50us.springboot.controller;


import com.in50us.springboot.model.User;
import com.in50us.springboot.service.UserService;
import com.in50us.springboot.util.CustomErrorType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RestApiController {

    public static final Logger logger = LoggerFactory.getLogger(RestApiController.class);

    @Autowired
    UserService userService;


    @RequestMapping(value = "/user/", method = RequestMethod.GET)
    public ResponseEntity<List<User>> listAllUsers() {
        List<User> users = userService.findAllUser();
        if (users.isEmpty()) {
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<User>>(users, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<User> getUser(@PathVariable("id") long id) {
        logger.info("Fetching User with id {}" + id);
        User user = userService.findById(id);
        if (user == null) {
            logger.info("User with id {} not found" + id);
            return new ResponseEntity(new CustomErrorType("User with id" + id + "not found"), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);
        if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with name {} already exsists", user.getName());
            return new ResponseEntity(new CustomErrorType("Unable to create. A User with name" + user.getName() + "already exsists."), HttpStatus.CONFLICT);
        }
        userService.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("api/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUser(@PathVariable("id") long id, @RequestBody User user) {
        logger.info("Updating user with id{}", id);
        User currentUser = userService.findById(id);
        if (currentUser == null) {
            logger.error("Unable to update. User with id{} not found", id);
            return new ResponseEntity<Object>(new CustomErrorType("Unable to update. User with id" + id + "not found"), HttpStatus.NOT_FOUND);
        }
        currentUser.setName(user.getName());
        currentUser.setAge(user.getAge());
        currentUser.setSalary(user.getSalary());
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
    }


    @RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id")long id){
        logger.info("Fetching & Deleting User with Id {}", id);
        User user = userService.findById(id);
        if(user == null){
            logger.error("Unable to delete. User with id {} not found", id);
            return new ResponseEntity<Object>(new CustomErrorType("Unable to delete. User with id"+id+"not found"), HttpStatus.NOT_FOUND);
        }
        userService.deletUserById(id);
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/user/", method = RequestMethod.DELETE)
    public ResponseEntity<User> deleteAllUsers(){
        logger.info("Deleting all users");
        userService.deleteAllUser();
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
    }


}
