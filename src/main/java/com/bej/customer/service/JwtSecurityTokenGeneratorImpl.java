package com.bej.customer.service;

import com.bej.customer.domain.Customer;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Service
public class JwtSecurityTokenGeneratorImpl implements SecurityTokenGenerator{

    //This method will generate the token


    // JWT: Jason Web Token
    // Header:: (alg, Issued at time(IAT) || Payload:: contains claims that have user information || Verify Sign
    @Override
    public Map<String, String> generateToken(Customer customer)
    {
        String jwtToken = null;
        jwtToken = Jwts.builder()
                .setSubject(customer.getCustomerName())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "secretkey").compact();


        Map<String,String> map = new HashMap<>();
        map.put("token", jwtToken);
        map.put("Customer Name: ", customer.getCustomerName());
        return map;
    }

}
