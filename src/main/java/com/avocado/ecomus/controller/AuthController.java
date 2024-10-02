package com.avocado.ecomus.controller;

import com.avocado.ecomus.exception.*;
import com.avocado.ecomus.jwt.JwtHelper;
import com.avocado.ecomus.payload.req.AuthReq;
import com.avocado.ecomus.payload.req.RegisterRequest;
import com.avocado.ecomus.payload.resp.BaseResp;
import com.avocado.ecomus.security.CustomUserDetails;
import com.avocado.ecomus.service.AuthService;
import com.avocado.ecomus.service.ConfirmationTokenService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private AuthService authService;

    private final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ConfirmationTokenService confirmationTokenService;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthReq req){
        BaseResp resp = new BaseResp();


        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.email(), req.password())
            );
            List<GrantedAuthority> authorities = (List<GrantedAuthority>) authentication.getAuthorities();
            HashMap<String, Object> userId = new HashMap<>();
            userId.put("userId", authentication.getPrincipal());
            resp.setData(jwtHelper.generateToken(mapper.writeValueAsString(authorities), userId));
            resp.setMsg("Authentication success");
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (BadCredentialsException | UserNotFoundException | UserNotVerifiedException e){
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.UNAUTHORIZED.value());
            return new ResponseEntity<>(resp, HttpStatus.OK);
        } catch (JsonProcessingException e) {
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        BaseResp resp = new BaseResp();

        try {
            authService.register(request);
            resp.setMsg("Registration success");
        }catch (UserAlreadyExistException | MessagingException e){
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @PostMapping("/confirm/{token}")
    public ResponseEntity<?> confirm(@PathVariable String token){
        BaseResp resp = new BaseResp();
        try {
            confirmationTokenService.confirmToken(token);
            resp.setMsg("Confirmation success");
        }catch (
                ConfirmationTokenExpirationException | ConfirmationTokenNotFoundException | TokenConfirmedException e
         ){
            resp.setMsg(e.getMessage());
            resp.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
}
