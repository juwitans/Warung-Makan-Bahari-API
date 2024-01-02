package com.enigma.warungmakanbahari.service.impl;

import com.enigma.warungmakanbahari.constant.ERole;
import com.enigma.warungmakanbahari.entity.Role;
import com.enigma.warungmakanbahari.repository.RoleRepository;
import com.enigma.warungmakanbahari.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Role getOrSave(ERole eRole) {
        Optional<Role> optionalRole = roleRepository.findByRole(eRole);
        if (optionalRole.isPresent()) return optionalRole.get();

        Role role = Role.builder()
                .role(eRole)
                .build();
        return roleRepository.save(role);
    }
}
