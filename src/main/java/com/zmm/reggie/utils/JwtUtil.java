package com.zmm.reggie.utils;

import com.alibaba.fastjson2.JSON;
//import com.zmm.reggie.entity.Employee;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {
    // 有效期
    private static final long JWT_EXPIRE = 24 * 60 * 60 * 1000L;  // 一天

    // 令牌密钥
    private static final String JWT_KEY = "123456";

    // 创建Jwt
    public static String createToken(Object data) {
        // 当前时间
        long currentTime = System.currentTimeMillis();
        // 过期时间
        long expTime = currentTime + JWT_EXPIRE;
        // 构建jwt
        JwtBuilder builder = Jwts.builder()
                .setId(UUID.randomUUID() + "")
                .setSubject(JSON.toJSONString(data))
                .setIssuer("system")
                .setIssuedAt(new Date(currentTime))
                .signWith(SignatureAlgorithm.HS256, encodeSecret(JWT_KEY))
                .setExpiration(new Date(expTime));
        return builder.compact();
    }

    private static SecretKey encodeSecret(String key) {
        byte[] encode = Base64.getEncoder().encode(key.getBytes());
        SecretKeySpec aes = new SecretKeySpec(encode, 0, encode.length, "AES");
        return aes;
    }

    // 解析Jwt
    public static Claims parseToken(String token) {
        Claims body = Jwts.parser()
                .setSigningKey(encodeSecret(JWT_KEY))
                .parseClaimsJws(token)
                .getBody();
        return body;
    }

    public static <T> T parseToken(String token, Class<T> clazz) {
        Claims body = Jwts.parser()
                .setSigningKey(encodeSecret(JWT_KEY))
                .parseClaimsJws(token)
                .getBody();
        return JSON.parseObject(body.getSubject(), clazz);
    }

//    public static void main(String[] args) {
//        Employee employee = new Employee();
//        employee.setUsername("jack");
//        employee.setPassword("12345");
//        String token = createToken(employee);
//        System.out.println(token);
//        Employee employee1 = parseToken(token, Employee.class);
//        System.out.println(employee1.toString());
//    }
}