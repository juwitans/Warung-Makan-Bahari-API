package com.enigma.warungmakanbahari.service.impl;

import com.enigma.warungmakanbahari.constant.ERole;
import com.enigma.warungmakanbahari.entity.Customer;
import com.enigma.warungmakanbahari.entity.Role;
import com.enigma.warungmakanbahari.entity.User;
import com.enigma.warungmakanbahari.model.request.LoginRequest;
import com.enigma.warungmakanbahari.model.request.RegisterRequest;
import com.enigma.warungmakanbahari.model.response.UserResponse;
import com.enigma.warungmakanbahari.repository.UserRepository;
import com.enigma.warungmakanbahari.security.JwtUtils;
import com.enigma.warungmakanbahari.service.AuthService;
import com.enigma.warungmakanbahari.service.CustomerService;
import com.enigma.warungmakanbahari.service.RoleService;
import com.enigma.warungmakanbahari.utils.ValidationUtils;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;
    private final ValidationUtils validationUtils;
    private final CustomerService customerService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @PostConstruct
    @Transactional(rollbackFor = Exception.class)
    public void initSuperAdmin() {
        Optional<User> optionalUser = userRepository.findByUsername("superadmin");

        if (optionalUser.isPresent()) return;

        Role roleSuperAdmin = roleService.getOrSave(ERole.ROLE_SUPER_ADMIN);
        Role roleAdmin = roleService.getOrSave(ERole.ROLE_ADMIN);
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);

        // hash password
        String hashPassword = passwordEncoder.encode("password");

        // simpan ke db
        User user = User.builder()
                .username("superadmin")
                .password(hashPassword)
                .roles(List.of(roleSuperAdmin, roleAdmin, roleCustomer))
                .build();
        userRepository.saveAndFlush(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse register(RegisterRequest request) {
        validationUtils.validate(request);
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .roles(List.of(roleCustomer))
                .build();

        userRepository.saveAndFlush(user);

        Customer customer = Customer.builder()
                .name(request.getCustomer().getName())
                .phoneNumber(request.getCustomer().getPhoneNumber())
                .address(request.getCustomer().getAddress())
                .user(user)
                .build();

        customerService.create(customer);

        return toUserResponse(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse registerAdmin(RegisterRequest request) {
        validationUtils.validate(request);
        Role roleCustomer = roleService.getOrSave(ERole.ROLE_CUSTOMER);
        Role roleAdmin = roleService.getOrSave(ERole.ROLE_ADMIN);

        String hashPassword = passwordEncoder.encode(request.getPassword());

        User user = User.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .roles(List.of(roleCustomer))
                .build();

        userRepository.saveAndFlush(user);

        Customer customer = Customer.builder()
                .name(request.getCustomer().getName())
                .phoneNumber(request.getCustomer().getPhoneNumber())
                .address(request.getCustomer().getAddress())
                .user(user)
                .build();

        customerService.create(customer);

        return toUserResponse(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String login(LoginRequest request) {
        validationUtils.validate(request);

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getUsername(), request.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authenticate);
        User user = (User) authenticate.getPrincipal();

        return jwtUtils.generateToken(user);
    }

    private static UserResponse toUserResponse(User user) {
        List<String> roles = user.getRoles().stream().map(role -> role.getRole().name()).toList();
        return UserResponse.builder()
                .username(user.getUsername())
                .roles(roles)
                .build();
    }
}
