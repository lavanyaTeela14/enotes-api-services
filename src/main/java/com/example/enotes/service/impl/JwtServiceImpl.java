package com.example.enotes.service.impl;

import com.example.enotes.entity.User;
import com.example.enotes.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
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
        claims.put("roles",user.getRoles());
        claims.put("status",user.getStatus().getIsActive());

        String token= Jwts.builder()
                .claims().add(claims)
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+60*60*10))
                .and()
                .signWith(getKey())
                .compact();
        return token;
    }

    private Key getKey() {
        byte[] key= Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(key);
    }
}
