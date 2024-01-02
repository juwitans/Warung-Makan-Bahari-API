package com.enigma.warungmakanbahari.model.request;

import lombok.*;

import java.time.Month;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchOrderRequest {
    private String transType;
    private Long productName;
    private Date day;
    private int month;
    private int year;
    private Date startDate;
    private Date endDate;
}
