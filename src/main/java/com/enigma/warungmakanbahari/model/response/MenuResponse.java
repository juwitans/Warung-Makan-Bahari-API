package com.enigma.warungmakanbahari.model.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuResponse {
    private String name;
    private Long price;
}
