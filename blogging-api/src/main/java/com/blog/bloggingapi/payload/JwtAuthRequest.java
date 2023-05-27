package com.blog.bloggingapi.payload;

import lombok.Data;

@Data
public class JwtAuthRequest {

    private String userName;
    private String password;
}
