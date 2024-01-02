package com.enigma.warungmakanbahari.service;

import com.enigma.warungmakanbahari.entity.Customer;
import com.enigma.warungmakanbahari.model.request.CustomerRequest;
import com.enigma.warungmakanbahari.model.response.CustomerResponse;

import java.util.List;

public interface CustomerService {
    Customer create(Customer customer);
    List<CustomerResponse> getAll(CustomerRequest request);
    CustomerResponse getById(String id);
    CustomerResponse update(Customer customer);
    void deleteById(String id);
}
