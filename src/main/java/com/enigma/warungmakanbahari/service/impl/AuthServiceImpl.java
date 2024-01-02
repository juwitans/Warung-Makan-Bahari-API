package com.enigma.warungmakanbahari.service.impl;

import com.enigma.warungmakanbahari.model.request.AuthRequest;
import com.enigma.warungmakanbahari.model.response.UserResponse;
import com.enigma.warungmakanbahari.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse register(AuthRequest request) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse registerAdmin(AuthRequest request) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String login(AuthRequest request) {
        return null;
    }
}
