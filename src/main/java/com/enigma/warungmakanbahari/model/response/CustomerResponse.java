package com.enigma.warungmakanbahari.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponse {
    private String name;
    private String phoneNumber;
    private String address;
}
