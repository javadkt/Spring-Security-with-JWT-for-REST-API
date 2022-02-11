package com.example.springsecurity.controller;


import com.example.springsecurity.model.AuthenticationRequest;
import com.example.springsecurity.model.AuthenticationResponse;
import com.example.springsecurity.model.User;
import com.example.springsecurity.service.UserService;
import com.example.springsecurity.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    UserService userDetailsService;


    @RequestMapping(value = "/auth", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthToken(@RequestBody AuthenticationRequest authenticationRequest) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                authenticationRequest.getPassword()));

        final User user = userService.getUser();
        final String jwtToken = jwtUtil.generateToken((UserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(new AuthenticationResponse(user, jwtToken));
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public String health() {
        return "OK";
    }

    @RequestMapping(value = "/registeruser", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody User user) {
        final String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        User userObj = userDetailsService.getUserByUserId(user.getUserId());
        if (userObj != null) return new ResponseEntity<>(HttpStatus.CONFLICT);
        User savedUser = userDetailsService.saveUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.OK);
    }
}
