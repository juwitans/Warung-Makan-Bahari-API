package com.enigma.warungmakanbahari.controller;

import com.enigma.warungmakanbahari.entity.Customer;
import com.enigma.warungmakanbahari.entity.Menu;
import com.enigma.warungmakanbahari.model.request.CustomerRequest;
import com.enigma.warungmakanbahari.model.request.MenuRequest;
import com.enigma.warungmakanbahari.model.response.CustomerResponse;
import com.enigma.warungmakanbahari.model.response.MenuResponse;
import com.enigma.warungmakanbahari.model.response.WebResponse;
import com.enigma.warungmakanbahari.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<?> getAllMenus(@RequestParam(required = false) String name) {
        CustomerRequest request = CustomerRequest.builder()
                .name(name)
                .build();

        List<CustomerResponse> customers = customerService.getAll(request);
        WebResponse<List<CustomerResponse>> response = WebResponse.<List<CustomerResponse>>builder()
                .message("successfully get all customers")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(customers)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable String id) {
        CustomerResponse customer = customerService.getById(id);
        WebResponse<CustomerResponse> response = WebResponse.<CustomerResponse>builder()
                .message("successfully get customer by id")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(customer)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateCustomer(@RequestBody Customer customer) {
        CustomerResponse customerResponse = customerService.update(customer);
        WebResponse<CustomerResponse> response = WebResponse.<CustomerResponse>builder()
                .message("successfully update menu")
                .status(HttpStatus.OK.getReasonPhrase())
                .data(customerResponse)
                .build();

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable String id) {
        customerService.deleteById(id);
        WebResponse<String> response = WebResponse.<String>builder()
                .message("successfully delete menu")
                .status(HttpStatus.OK.getReasonPhrase())
                .data("menu deleted")
                .build();

        return ResponseEntity.ok(response);
    }
}
