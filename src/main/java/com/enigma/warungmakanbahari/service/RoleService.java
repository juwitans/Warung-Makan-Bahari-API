package com.enigma.warungmakanbahari.service;

import com.enigma.warungmakanbahari.constant.ERole;
import com.enigma.warungmakanbahari.entity.Role;

public interface RoleService {
    Role getOrSave(ERole role);
}
