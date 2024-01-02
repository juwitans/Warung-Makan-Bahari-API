package com.enigma.warungmakanbahari.controller;

import com.enigma.warungmakanbahari.entity.Order;
import com.enigma.warungmakanbahari.model.request.OrderRequest;
import com.enigma.warungmakanbahari.model.request.SearchOrderRequest;
import com.enigma.warungmakanbahari.model.response.WebResponse;
import com.enigma.warungmakanbahari.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(path = "/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<?> createNewOrder(@RequestBody OrderRequest request) {
        Order order = orderService.create(request);
        WebResponse<Order> response = WebResponse.<Order>builder()
                .message("successfully create new order")
                .status(HttpStatus.CREATED.getReasonPhrase())
                .data(order)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','CUSTOMER')")
    @GetMapping
    public ResponseEntity<?> getOrders(@RequestParam(required = false) String transType,
                                       @RequestParam(required = false) Long productName,
                                       @RequestParam(required = false) Date day,
                                       @RequestParam(required = false) int month,
                                       @RequestParam(required = false) int year,
                                       @RequestParam(required = false) Date startDate,
                                       @RequestParam(required = false) Date endDate
                                       ) {
        SearchOrderRequest request = SearchOrderRequest.builder()
                .transType(transType)
                .productName(productName)
                .day(day)
                .month(month)
                .year(year)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        List<Order> orders = orderService.getAll(request);
        WebResponse<List<Order>> response = WebResponse.<List<Order>>builder()
                .message("successfully get all orders")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(orders)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','CUSTOMER')")
    @GetMapping(path = "/:{id}")
    public ResponseEntity<?> getOrderById(@PathVariable String id) {
        Order order = orderService.getById(id);
        WebResponse<Order> response = WebResponse.<Order>builder()
                .message("successfully get order by id")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(order)
                .build();
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN','CUSTOMER')")
    @GetMapping(path = "/:{customerId}")
    public ResponseEntity<?> getOrderByCustomerId(@PathVariable String customerId) {
        List<Order> orders = orderService.getByCustomerId(customerId);
        WebResponse<List<Order>> response = WebResponse.<List<Order>>builder()
                .message("successfully get order by id")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(orders)
                .build();
        return ResponseEntity.ok(response);
    }
}
