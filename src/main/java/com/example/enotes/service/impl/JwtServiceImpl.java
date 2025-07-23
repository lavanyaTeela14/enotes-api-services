package com.example.enotes.service.impl;

import com.example.enotes.entity.User;
import com.example.enotes.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.*;

@Service
public class JwtServiceImpl implements JwtService {

    private String secretKey="";

    public JwtServiceImpl() {
        try{
            KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
            SecretKey sk= keyGen.generateKey();
            secretKey= Base64.getEncoder().encodeToString(sk.getEncoded());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String generatetoken(User user) {
        Map<String, Object> claims=new HashMap<>();
        claims.put("id",user.getId());
        claims.put("roles",user.getRoles());
        claims.put("status",user.getStatus().getIsActive());

        String token= Jwts.builder()
                .claims().add(claims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*60*10))
                .and()
                .signWith(getKey())
                .compact();
        return token;
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username=extractUserName(token);
        Boolean isExpired=isTokenExpired(token);
        if(username.equalsIgnoreCase(userDetails.getUsername()) && !isExpired)
        {
            return true;
        }
        return false;
    }

    private Boolean isTokenExpired(String token) {
        Claims claims=extractAllClaims(token);
        Date expiredDate=claims.getExpiration();
        return expiredDate.before(new Date());
    }

    @Override
    public String extractUserName(String token) {
        Claims claims=extractAllClaims(token);
        return claims.getSubject();
    }

    public String role(String token)
    {
        Claims claims=extractAllClaims(token);
        return (String)claims.get("roles");
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(decryptKey(secretKey)).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey decryptKey(String secretKey) {
        byte[] decode = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(decode);
    }

    private Key getKey() {
        byte[] key= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }
}
