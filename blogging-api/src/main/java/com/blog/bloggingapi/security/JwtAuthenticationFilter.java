package com.blog.bloggingapi.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        //get token
        String requestToken= request.getHeader("Authorization");

        //bearer 223455jhjhj
        System.out.println(requestToken);
        String userName=null;
        String token = null;

        if(request != null && requestToken.startsWith("Bearer ")){

token = requestToken.substring(7);
try {
    userName = this.jwtTokenHelper.getUserNameFromToken(token);

}catch (IllegalArgumentException e){
    System.out.println("unable to get jwt token");
}
catch (ExpiredJwtException e){
    System.out.println("jwt token expired");
}
catch (MalformedJwtException e){
    System.out.println("invalid jwt exception");
}

        }
        else {
            System.out.println("jwt token does not begin with bearer");
        }


        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails= this.userDetailsService.loadUserByUsername(userName);
            if(this.jwtTokenHelper.validateToken(token,userDetails)){

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

            }else {
                System.out.println("ivalid jwt token");
            }


        }else {

            System.out.println("username is null");
        }
filterChain.doFilter(request,response);

    }
}
