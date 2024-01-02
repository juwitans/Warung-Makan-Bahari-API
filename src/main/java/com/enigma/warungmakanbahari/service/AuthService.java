package com.enigma.warungmakanbahari.service;

import com.enigma.warungmakanbahari.model.request.LoginRequest;
import com.enigma.warungmakanbahari.model.request.RegisterRequest;
import com.enigma.warungmakanbahari.model.response.UserResponse;

public interface AuthService {
    UserResponse register(RegisterRequest request);
    UserResponse registerAdmin(RegisterRequest request);
    String login(LoginRequest request);
}
