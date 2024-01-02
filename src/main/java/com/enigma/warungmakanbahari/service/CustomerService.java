package com.enigma.warungmakanbahari.service;

import com.enigma.warungmakanbahari.entity.Customer;

import java.util.List;

public interface CustomerService {
    Customer create(Customer customer);
    List<Customer> getAll();
    Customer getById(String id);
    Customer update(Customer customer);
    void deleteById(String id);
}
