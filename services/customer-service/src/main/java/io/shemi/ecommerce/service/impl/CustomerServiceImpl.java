package io.shemi.ecommerce.service.impl;

import io.shemi.ecommerce.dto.request.CreateCustomerRequest;
import io.shemi.ecommerce.dto.request.UpdateCustomerRequest;
import io.shemi.ecommerce.dto.response.CustomerResponse;
import io.shemi.ecommerce.entity.Address;
import io.shemi.ecommerce.entity.Customer;
import io.shemi.ecommerce.exception.CustomerNotFoundException;
import io.shemi.ecommerce.exception.DuplicateEmailException;
import io.shemi.ecommerce.mapper.CustomerMapper;
import io.shemi.ecommerce.repository.CustomerRepository;
import io.shemi.ecommerce.service.CustomerService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CreateCustomerRequest customerRequest) {
        Customer existingWithEmail = customerRepository.findByEmail(customerRequest.email());
        if (existingWithEmail != null) {
            throw new DuplicateEmailException(customerRequest.email());
        }
        Customer customer = CustomerMapper.toCustomer(customerRequest);
        Customer saved = customerRepository.save(customer);
        return CustomerMapper.toResponse(saved);
    }

    @Cacheable("customers")
    public List<CustomerResponse> findAllCustomers(String search, int skip, int limit) {
        log.info("--- Executing findAllCustomers method. If you see this more than once, caching is NOT working. ---");

        List<Customer> customers;

        if (search != null && !search.isBlank()) {
            customers = customerRepository
                    .findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(search, search);
        } else {
            customers = (List<Customer>) customerRepository.findAll();
        }
        log.info("customers = {}", customers);
        return customers.stream()
                .skip(skip)
                .limit(limit)
                .map(CustomerMapper::toResponse)
                .collect(Collectors.toList());
    }

//    @Cacheable(value = "customers")
    public CustomerResponse findCustomerById(String id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new CustomerNotFoundException(id));
        return CustomerMapper.toResponse(customer);
    }

//    @Cacheable(value = "customersByEmail", key = "#email")
    public CustomerResponse findCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new CustomerNotFoundException(email);
        }
        return CustomerMapper.toResponse(customer);
    }

//    @CacheEvict(value = {"customers", "customersByEmail"}, key = "#id")
    public CustomerResponse updateCustomer(String id, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));

        if (updateCustomerRequest.firstName() != null) {
            customer.setFirstName(updateCustomerRequest.firstName());
        }
        if (updateCustomerRequest.lastName() != null) {
            customer.setLastName(updateCustomerRequest.lastName());
        }
        if (updateCustomerRequest.phoneNumber() != null) {
            customer.setPhoneNumber(updateCustomerRequest.phoneNumber());
        }
        if (updateCustomerRequest.preferredLanguage() != null) {
            customer.setPreferredLanguage(updateCustomerRequest.preferredLanguage());
        }
        if (updateCustomerRequest.preferredCurrency() != null) {
            customer.setPreferredCurrency(updateCustomerRequest.preferredCurrency());
        }
        if (updateCustomerRequest.profileImageUrl() != null) {
            customer.setProfileImageUrl(updateCustomerRequest.profileImageUrl());
        }
        if (updateCustomerRequest.address() != null) {
            var addrReq = updateCustomerRequest.address();
            var address = customer.getAddress();

            if (address == null) {
                address = new Address();
                customer.setAddress(address);
            }

            if (addrReq.street() != null) {
                address.setStreet(addrReq.street());
            }
            if (addrReq.city() != null) {
                address.setCity(addrReq.city());
            }
            if (addrReq.state() != null) {
                address.setState(addrReq.state());
            }
            if (addrReq.postalCode() != null) {
                address.setPostalCode(addrReq.postalCode());
            }
            if (addrReq.country() != null) {
                address.setCountry(addrReq.country());
            }
        }
        customer.setUpdatedAt(LocalDateTime.now());
        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerMapper.toResponse(updatedCustomer);
    }

//    @Caching(evict = {
//            @CacheEvict(value = "customers", key = "#id"),
//            @CacheEvict(value = "customersByEmail", key = "#customer.email")
//    })
    public String deleteCustomerById(String id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
        if (customer.getAddress() != null) {
            customer.setAddress(null);
        }
        customerRepository.delete(customer);
        return "Customer with id: " + id + " has been deleted";
    }
}