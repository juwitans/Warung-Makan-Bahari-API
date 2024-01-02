package com.enigma.warungmakanbahari.service;

import com.enigma.warungmakanbahari.entity.Order;
import com.enigma.warungmakanbahari.model.request.OrderRequest;
import com.enigma.warungmakanbahari.model.request.SearchOrderRequest;

import java.util.List;

public interface OrderService {
    Order create(OrderRequest request);
    Order getById(String id);
    List<Order> getAll(SearchOrderRequest request);
}
