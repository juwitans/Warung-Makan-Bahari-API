package com.enigma.warungmakanbahari.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerRequest {
    private String name;
    private String phoneNumber;
    private String address;
}
