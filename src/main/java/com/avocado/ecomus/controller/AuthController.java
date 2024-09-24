package com.avocado.ecomus.controller;

import com.avocado.ecomus.exception.UserNotFoundException;
import com.avocado.ecomus.jwt.JwtHelper;
import com.avocado.ecomus.payload.req.AuthReq;
import com.avocado.ecomus.payload.resp.BaseResp;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    private ObjectMapper mapper = new ObjectMapper();

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthReq req){
        BaseResp resp = new BaseResp();


        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.email(), req.password())
            );
            List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
            resp.setData(jwtHelper.generateToken(mapper.writeValueAsString(authorities)));
            resp.setMsg("Authentication success");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (BadCredentialsException | UserNotFoundException e){
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }

    }
}
