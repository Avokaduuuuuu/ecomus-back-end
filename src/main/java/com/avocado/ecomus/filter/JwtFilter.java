package com.avocado.ecomus.filter;

import com.avocado.ecomus.dto.AuthorityDto;
import com.avocado.ecomus.jwt.JwtHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtHelper jwtHelper;

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.replace("Bearer ", "");

        String subject = jwtHelper.parseSubject(token);

        if (subject != null){
            List<AuthorityDto> authority = mapper.readValue(subject, new TypeReference<List<AuthorityDto>>(){});

            List<SimpleGrantedAuthority> grantedAuthorities = authority
                    .stream()
                    .map(authorityDto -> new SimpleGrantedAuthority(authorityDto.getAuthority()))
                    .toList();

            SecurityContext context = SecurityContextHolder.getContext();

            context.setAuthentication(new UsernamePasswordAuthenticationToken("", "", grantedAuthorities));
        }

        filterChain.doFilter(request, response);
    }
}
