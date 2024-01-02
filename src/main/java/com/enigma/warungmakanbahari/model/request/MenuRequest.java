package com.enigma.warungmakanbahari.model.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MenuRequest {
    private String name;
    private Long minPrice;
    private Long maxPrice;
}
