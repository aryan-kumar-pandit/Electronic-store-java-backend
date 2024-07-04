package com.lcwd.electronic.store.controllers;

import com.lcwd.electronic.store.dtos.ApiResponseMessage;
import com.lcwd.electronic.store.dtos.UserDto;
import com.lcwd.electronic.store.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    //create
    @Autowired
    private UserService userService;
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto userDto1 = userService.createUser(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }

    //update
    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") String userId,@RequestBody UserDto userDto)
    {
        UserDto updatedUserDto = userService.updateUser(userDto,userId);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    //delete

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponseMessage> deleteUser(@PathVariable String userId)
    {
        ApiResponseMessage message = ApiResponseMessage.builder()
                .message("User deleted successfully")
                .success(true)
                .status(HttpStatus.OK).build();
        userService.deleteUser(userId);
        return new ResponseEntity<>(message,HttpStatus.OK);
    }

    //get all
    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUser()
    {
        return new ResponseEntity<>(userService.getAllUser(),HttpStatus.OK);
    }

    //get single
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId)
    {
        return new ResponseEntity<>(userService.getUserById(userId),HttpStatus.OK);
    }

    //get by email
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email)
    {
        return new ResponseEntity<>(userService.getUserByEmail(email),HttpStatus.OK);
    }

    //search user

    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords)
    {
        return new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
    }

}
