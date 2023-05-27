package com.blog.bloggingapi.controller;

import com.blog.bloggingapi.payload.ApiResponse;
import com.blog.bloggingapi.payload.UserDto;
import com.blog.bloggingapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/created")
    public ResponseEntity<UserDto> createdUser(@Valid @RequestBody UserDto userDTO){

        UserDto createUserDto = this.userService.createUser(userDTO);
return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
    }

@PutMapping("/{userId}")
    ResponseEntity<UserDto> updateEntity(@Valid @RequestBody UserDto userDTO, @PathVariable ("userId") Integer userId){

    UserDto updatedUer = this.userService.updateUser(userDTO, userId);
    return ResponseEntity.ok(updatedUer);

}
@DeleteMapping("/{userId}")
public  ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uId){
this.userService.deleteUser(uId);
//return  new ResponseEntity(Map.of("message","user deleted"),HttpStatus.OK);

    return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully",true),HttpStatus.OK);
}
@GetMapping("/")
public ResponseEntity<List<UserDto>> getAllUser(){

        return ResponseEntity.ok(this.userService.getAllUser());

}

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Integer userId){

        return ResponseEntity.ok(this.userService.getUserbyID(userId));

    }

}
