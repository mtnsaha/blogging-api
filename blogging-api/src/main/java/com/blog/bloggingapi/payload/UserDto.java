package com.blog.bloggingapi.payload;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Integer id;
    @NotEmpty
    @Size(min=4,message = "user name must be minimum 4 charecter")
    private String name;
    @Email(message = "email address is not vaid")
    private String email;
    @NotEmpty
    @Size(min=3,max = 10, message = "passward must ben between 3 to 10 digit")
    //@Pattern()
    private String password;
    @NotEmpty
    private String about;
}
