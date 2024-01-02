package com.enigma.warungmakanbahari.service.impl;

import com.enigma.warungmakanbahari.entity.Customer;
import com.enigma.warungmakanbahari.entity.User;
import com.enigma.warungmakanbahari.repository.CustomerRepository;
import com.enigma.warungmakanbahari.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;

    @Override
    public Customer create(Customer customer) {
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer getById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) return optionalCustomer.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");
    }

    @Override
    public Customer update(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
        if (optionalCustomer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User credential = optionalCustomer.get().getUser();

        if (!currentUser.getId().equals(credential.getId())) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden");

        customer.setUser(credential);

        return customerRepository.save(customer);
    }

    @Override
    public void deleteById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User credential = optionalCustomer.get().getUser();

        if (!currentUser.getId().equals(credential.getId())) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden");

        Customer customer = optionalCustomer.get();
        customerRepository.delete(customer);
    }
}
