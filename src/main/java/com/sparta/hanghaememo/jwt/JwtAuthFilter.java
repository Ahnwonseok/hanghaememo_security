package com.sparta.hanghaememo.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.hanghaememo.dto.SecurityExceptionDto;
import io.jsonwebtoken.Claims;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
///////모든 API 요청을 할 때 마다 JWT 를 검증한다////////
public class JwtAuthFilter extends OncePerRequestFilter {

    private final com.sparta.hanghaememo.jwt.JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //토큰을 받는다.
        String token = jwtUtil.resolveToken(request);

        if(token != null) {
            if(!jwtUtil.validateToken(token)){ //유효한 토큰이 아니면
                jwtExceptionHandler(response, "Token Error", HttpStatus.UNAUTHORIZED.value());
                return;
            }
            Claims info = jwtUtil.getUserInfoFromToken(token);
            //유효한 토큰임이 판정됐으면 SecurityContextHolder 객체를 생성
            setAuthentication(info.getSubject());
        }
        //다음 필터로
        filterChain.doFilter(request,response);
    }

    public void setAuthentication(String username) {
        //SecurityContextHolder 객체를 생성
        Authentication authentication = jwtUtil.createAuthentication(username); //Authentication 반환
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(new SecurityExceptionDto(statusCode, msg));
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}