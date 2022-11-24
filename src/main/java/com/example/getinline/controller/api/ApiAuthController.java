package com.example.getinline.controller.api;

import com.example.getinline.dto.AdminRequest;
import com.example.getinline.dto.ApiDataResponse;
import com.example.getinline.dto.LoginRequest;
import org.springframework.web.bind.annotation.*;

//@RequestMapping("/api")
//@RestController
public class ApiAuthController {

    @PostMapping("/sign-up")
    public ApiDataResponse<String> signUp(@RequestBody AdminRequest adminRequest) {
        return ApiDataResponse.empty();
    }

    @PostMapping("/login")
    public ApiDataResponse<String> login(@RequestBody LoginRequest loginRequest) {
        return ApiDataResponse.empty();
    }
}
