package com.enigma.warungmakanbahari.service.impl;

import com.enigma.warungmakanbahari.entity.Customer;
import com.enigma.warungmakanbahari.entity.User;
import com.enigma.warungmakanbahari.model.request.CustomerRequest;
import com.enigma.warungmakanbahari.model.response.CustomerResponse;
import com.enigma.warungmakanbahari.repository.CustomerRepository;
import com.enigma.warungmakanbahari.service.CustomerService;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
    public List<CustomerResponse> getAll(CustomerRequest request) {
        Specification<Customer> specification = getCustomerSpecification(request);
        List<Customer> customers = customerRepository.findAll(specification);
        List<CustomerResponse> responses = new ArrayList<>();
        for (Customer customer : customers) {
            responses.add(getCustomerResponse(customer));
        }
        return responses;
    }

    @Override
    public CustomerResponse getById(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            return getCustomerResponse(optionalCustomer.get());}
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");
    }

    @Override
    public Customer get(String id) {
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) return optionalCustomer.get();
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");
    }

    @Override
    public CustomerResponse update(Customer customer) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customer.getId());
        if (optionalCustomer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");

        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User credential = optionalCustomer.get().getUser();

        if (!currentUser.getId().equals(credential.getId())) throw new ResponseStatusException(HttpStatus.FORBIDDEN, "forbidden");

        customer.setUser(credential);

        Customer updatedCustomer = customerRepository.save(customer);
        return getCustomerResponse(updatedCustomer);
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

    private Specification<Customer> getCustomerSpecification(CustomerRequest request) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getName() != null) {
                Predicate namePredicate = criteriaBuilder.like(
                        root.get("name"),
                        "%" + request.getName() + "%"
                );
                predicates.add(namePredicate);
            }
            return query.where(predicates.toArray(new Predicate[] {})).getRestriction();
        };
    }

    private CustomerResponse getCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .name(customer.getName())
                .phoneNumber(customer.getPhoneNumber())
                .address(customer.getAddress())
                .build();
    }
}
