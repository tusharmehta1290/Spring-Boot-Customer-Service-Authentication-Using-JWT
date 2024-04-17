package com.bej.customer.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class JwtFilter extends GenericFilter
{
    /*
     * Override the doFilter method of GenericFilterBean.
     * Create HttpServletRequest , HttpServletResponse and ServletOutputStream object
     * Retrieve the "authorization" header from the HttpServletRequest object.
     * Retrieve the "Bearer" token from "authorization" header.
     * If authorization header is invalid, throw Exception with message.
     * Parse the JWT token and get claims from the token using the secret key
     * Set the request attribute with the retrieved claims
     * Call FilterChain object's doFilter() method */

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException
    {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer"))
            throw new ServletException("Invalid or incorrect token is passed");

        else
        {

            String token = authHeader.substring(7);
            Claims claims = Jwts.parser()
                                .setSigningKey("secretkey")
                                .parseClaimsJws(token)
                                .getBody();

            //pass the claims in the request or anyone waiting to use it
            request.setAttribute("claims", claims);

            // passing the request to the controller
            filterChain.doFilter(request,response);

        }

    }
}

