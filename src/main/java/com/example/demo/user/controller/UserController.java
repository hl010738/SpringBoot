package com.example.demo.user.controller;

import com.example.demo.common.ResponseData;
import com.example.demo.user.entity.User;
import com.example.demo.user.repository.UserRepositroy;
import com.example.demo.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepositroy userRepositroy;

    @Autowired
    private UserService userService;

    @SuppressWarnings("unchecked")
    @ResponseBody
    @RequestMapping(path = "/user/login", method = RequestMethod.POST)
    public ResponseEntity<ResponseData<Object>> login(@RequestBody User u){


        ResponseData responseData = new ResponseData();
        List<User> userList = userService.findByEmail(u.getEmail());
        if(userList.size() == 0){
            responseData.setStatus("4001");
            responseData.setMsg("Email has not exits");
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(responseData);
        }

        User user = userList.get(0);
        if (!u.getPassword().equals(user.getPassword())){
            responseData.setStatus("4002");
            responseData.setMsg("Password is not correct");
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(responseData);
        }

        responseData = responseData.toResponseData(user);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(responseData);
    }

    @ResponseBody
    @RequestMapping(path = "/user/register", method = RequestMethod.POST)
    public ResponseEntity<ResponseData> register(@RequestBody User u){

        String email = u.getEmail();
        String pwd = u.getPassword();
        String phone = u.getPhone();
        String firstName = u.getFirstName();
        String lastName = u.getLastName();
        String userType = u.getUserType();

        List<User> userList = userService.findByEmail(email);
        if(userList.size() > 0){
            ResponseData responseData = new ResponseData();
            responseData.setStatus("4000");
            responseData.setMsg("Email has exits");
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(responseData);
        }

        User user = new User();
        user.setEmail(email);
        user.setPassword(pwd);
        user.setPhone(phone);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserType(userType);
        user.setFlag("0");
        user = userRepositroy.save(user);

        ResponseData responseData = new ResponseData();
        responseData = responseData.toResponseData(user);
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON_UTF8).body(responseData);
    }
}
