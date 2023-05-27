package com.blog.bloggingapi.security;

import com.blog.bloggingapi.entities.User;
import com.blog.bloggingapi.exception.ResourceNotFoundException;
import com.blog.bloggingapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //create a custom exception
        User user = this.userRepository.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("user", "email" + username, 0));
        return user;
    }
}
