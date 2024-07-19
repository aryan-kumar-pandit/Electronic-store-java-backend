package com.lcwd.electronic.store.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//this class is used to perform jwt operations,like to fetch username

@Component
public class JwtHelper {
    //1. token validity time in millisecond
    public static final long TOKEN_VALIDITY=5*60*60*1000;

    //2. secret_key
    public static final String SECRET_KEY="qwertyuiopasdfghjklzxcvbnmqwertyuiopasdfghjklzxcvbnmsdkjjdvjdvdbjdjvbsdjvbsjdbvbvbsdbvbvbdbv";

    //retrieve username from jwt token
    public String getUsernameFromToken(String token)
    {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public <T> T getClaimFromToken(String token, Function<Claims,T> claimsResolver)
    {
        final Claims claims=getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieving all the information from token we will need secrete key
    public Claims getAllClaimsFromToken(String token)
    {
        //return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        return Jwts.parser().setSigningKey(SECRET_KEY).build().parseClaimsJws(token).getPayload();
    }

    //to check if token is expired
    public boolean isTokenExpired(String token)
    {
        final Date expiration=getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //get Expiration date from token
    public Date getExpirationDateFromToken(String token)
    {
        return getClaimFromToken(token,Claims::getExpiration);
    }

    //generate token
    public String generateToken(UserDetails userDetails)
    {
        Map<String,Object> claims=new HashMap<>();
        return doGenerateToken(claims,userDetails.getUsername());
    }

    private String doGenerateToken(Map<String,Object> claims,String subject)
    {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+TOKEN_VALIDITY))
                .signWith(SignatureAlgorithm.HS512,SECRET_KEY)
                .compact();
    }

}
