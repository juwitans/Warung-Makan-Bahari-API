package com.enigma.warungmakanbahari.model.request;

import com.enigma.warungmakanbahari.entity.Customer;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username must only contain alphanumeric characters and underscores")
    private String username;
    @Size(min = 6, message = "password minimum 6 character")
    private String password;
    private CustomerRequest customer;
}
