//package com.cloudnativewebapp.webapp.Exception;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@WebFilter
//public class HttpRequestException extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String allowed = "/healthz";
//        if(allowed.equals(request.getRequestURI())) {
//            filterChain.doFilter(request, response);
//        }
//        else {
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//        }
//    }
//}
