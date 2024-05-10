package com.example.JavaCourseWork.controller;

import com.example.JavaCourseWork.model.DTO.UserDTO;
import com.example.JavaCourseWork.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authorizationService;

    @PostMapping("/signup")
    public String signUp(@RequestBody UserDTO userData){
        if(authorizationService.signUp(userData)){
            return "User created";
        }else{
            return "User exists";
        }
    }

    @PostMapping("/signin")
    public String signIn(@RequestBody UserDTO userData, HttpServletRequest request, HttpServletResponse response){
        if(authorizationService.signIn(userData, request, response)){
            return "Signed in successfully";
        }else{
            return "Bad credentials";
        }
    }
}
