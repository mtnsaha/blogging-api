package com.blog.bloggingapi.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtTokenHelper {

    public static final long JWT_TOKEN_VALIDITY= 5*60*60;

    private String secret= "jwtTokenKey";
    //retrieve usr name from jwt token
    public String getUserNameFromToken(String token){
return getClaimFromToken(token, Claims::getSubject);

    }
//retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);

    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> claimResolver){
        final Claims claims= getAllClaimFromToken(token);
        return claimResolver.apply(claims);

    }

    public Claims getAllClaimFromToken(String token){

        return Jwts.parser().setSigningKey(secret).parseClaimsJwt(token).getBody();
    }

    public Boolean isTokenExpired(String token){
final Date expiration =getExpirationDateFromToken(token);
return expiration.before(new Date());

    }

//generate token for user
    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return doGeneratedToken(claims,userDetails.getUsername());
    }

//while creating the token
    private String doGeneratedToken(Map<String, Object> claims, String subject){

        return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+JWT_TOKEN_VALIDITY*100))
                .signWith(SignatureAlgorithm.HS512,secret).compact();
    }

    //validate token
    public boolean validateToken(String token, UserDetails userDetails){
final String userName= getUserNameFromToken(token);
return (userName.equals(userDetails.getUsername())&&!isTokenExpired(token));

    }
}
