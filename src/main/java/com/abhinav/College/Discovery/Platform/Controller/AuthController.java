package com.abhinav.College.Discovery.Platform.Controller;

import com.abhinav.College.Discovery.Platform.Config.JwtUtils;
import com.abhinav.College.Discovery.Platform.Models.Users;
import com.abhinav.College.Discovery.Platform.Repository.UserRepo;
import com.abhinav.College.Discovery.Platform.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Users loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        Users user = userRepo.findByusername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String token = jwtUtils.generateToken(authentication.getName());
        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        Map<String, Object> userDetails = new HashMap<>();
        userDetails.put("username", user.getUsername());
        userDetails.put("id",user.getId());

        response.put("user", userDetails);

        return response;
    }
    @PostMapping("/signup")
    public Users register(@RequestBody Users user){
        return userService.registerUser(user);
    }

}
