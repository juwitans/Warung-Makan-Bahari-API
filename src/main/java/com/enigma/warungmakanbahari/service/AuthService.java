package com.enigma.warungmakanbahari.service;

import com.enigma.warungmakanbahari.model.request.AuthRequest;
import com.enigma.warungmakanbahari.model.response.UserResponse;

public interface AuthService {
    UserResponse register(AuthRequest request);
    UserResponse registerAdmin(AuthRequest request);
    String login(AuthRequest request);
}
