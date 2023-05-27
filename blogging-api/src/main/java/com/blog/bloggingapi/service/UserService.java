package com.blog.bloggingapi.service;



import com.blog.bloggingapi.payload.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDTO);

    UserDto updateUser(UserDto userDTO, Integer userId);

    UserDto getUserbyID(Integer userID);

    List<UserDto> getAllUser();
    void deleteUser(Integer userID);

}
