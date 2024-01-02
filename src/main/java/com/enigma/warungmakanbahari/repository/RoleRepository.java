package com.enigma.warungmakanbahari.repository;

import com.enigma.warungmakanbahari.constant.ERole;
import com.enigma.warungmakanbahari.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(ERole role);
}
