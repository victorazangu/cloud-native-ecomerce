package io.shemi.ecommerce.controller;

import io.shemi.ecommerce.dto.request.CreateCustomerRequest;
import io.shemi.ecommerce.dto.request.UpdateCustomerRequest;
import io.shemi.ecommerce.dto.response.CustomerResponse;
import io.shemi.ecommerce.service.impl.CustomerServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor

public class CustomerController {

    @Autowired
    private final CustomerServiceImpl customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody @Valid CreateCustomerRequest customer) {
        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAllCustomers());
    }

    @GetMapping("{id}")
    public ResponseEntity<CustomerResponse> getCustomerById(@PathVariable("id") String id) {
        return ResponseEntity.ok(customerService.findCustomerById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<CustomerResponse> getCustomersByEmail(@PathVariable("email") String email) {
        return ResponseEntity.ok(customerService.findCustomerByEmail(email));
    }

    @PatchMapping("{id}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable("id") String id,
            @RequestBody @Valid UpdateCustomerRequest updateCustomerRequest) {
        return ResponseEntity.ok(customerService.updateCustomer(id, updateCustomerRequest));

    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable("id") String id) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(customerService.deleteCustomerById(id));
    }
}
