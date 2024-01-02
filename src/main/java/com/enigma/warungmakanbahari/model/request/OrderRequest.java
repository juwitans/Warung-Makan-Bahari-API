package com.enigma.warungmakanbahari.model.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderRequest {
    private String customerId;
    private List<OrderDetailRequest> orderDetails;
    private String transType;
    private String tableId;
}
