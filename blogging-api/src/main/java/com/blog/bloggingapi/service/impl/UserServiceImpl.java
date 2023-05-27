package com.blog.bloggingapi.service.impl;


import com.blog.bloggingapi.entities.User;
import com.blog.bloggingapi.exception.ResourceNotFoundException;
import com.blog.bloggingapi.payload.UserDto;
import com.blog.bloggingapi.repository.UserRepository;
import com.blog.bloggingapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDTO) {

        User user = this.dtotoUser(userDTO);
        User savedUser = this.userRepository.save(user);
        return this.userToDto(savedUser);
    }

    @Override
    public UserDto updateUser(UserDto userDTO, Integer userId) {

        User user = this.userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
        user.setName(userDTO.getName());
        user.setName(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        User updatedUser = this.userRepository.save(user);
        return this.userToDto(updatedUser);


    }

    @Override
    public UserDto getUserbyID(Integer userID) {
        User user = this.userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User", "id", userID));
        return this.userToDto(user);
    }

    @Override
    public List<UserDto> getAllUser() {

        List<User> users = this.userRepository.findAll();

        List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
        return userDtos;
    }

    @Override
    public void deleteUser(Integer userID) {

        User user = this.userRepository.findById(userID).orElseThrow(() -> new ResourceNotFoundException("user", "id", userID));

        this.userRepository.delete(user);
    }

    public User dtotoUser(UserDto userDTO) {

        User user = this.modelMapper.map(userDTO,User.class);
        /*User user = new User();

        user.setId(userDTO.getId());
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());*/

        return user;
    }

    public UserDto userToDto(User user) {
        UserDto userDTO = this.modelMapper.map(user, UserDto.class);
        /*UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        userDTO.setAbout(user.getAbout());*/


        return userDTO;


    }


}
