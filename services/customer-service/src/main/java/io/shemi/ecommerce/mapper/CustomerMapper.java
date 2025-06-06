package io.shemi.ecommerce.mapper;

import io.shemi.ecommerce.dto.request.CreateCustomerRequest;
import io.shemi.ecommerce.dto.response.AddressResponse;
import io.shemi.ecommerce.dto.response.CustomerResponse;
import io.shemi.ecommerce.entity.Address;
import io.shemi.ecommerce.entity.Customer;

import java.time.LocalDateTime;

public class CustomerMapper {

    public static Customer toCustomer(CreateCustomerRequest request) {
        if (request == null) return null;

        return Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .username(request.username())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .password(request.password())
                .address(toAddress(request.address()))
                .preferredLanguage(request.preferredLanguage())
                .preferredCurrency(request.preferredCurrency())
                .profileImageUrl(request.profileImageUrl())
                .emailVerified(false)
                .isActive(true)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    public static Address toAddress(io.shemi.ecommerce.dto.request.CreateAddressRequest request) {
        if (request == null) return null;
        return Address.builder()
                .street(request.street())
                .city(request.city())
                .state(request.state())
                .postalCode(request.postalCode())
                .country(request.country())
                .build();
    }

    public static CustomerResponse toResponse(Customer customer) {
        if (customer == null) return null;
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .username(customer.getUsername())
                .email(customer.getEmail())
                .phoneNumber(customer.getPhoneNumber())
                .address(toAddressResponse(customer.getAddress()))
                .preferredLanguage(customer.getPreferredLanguage())
                .preferredCurrency(customer.getPreferredCurrency())
                .profileImageUrl(customer.getProfileImageUrl())
                .emailVerified(customer.getEmailVerified())
                .isActive(customer.getIsActive())
                .createdAt(customer.getCreatedAt())
                .updatedAt(customer.getUpdatedAt())
                .referralCode(customer.getReferralCode())
                .build();
    }

    public static AddressResponse toAddressResponse(Address address) {
        if (address == null) return null;
        return AddressResponse.builder()
                .street(address.getStreet())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .build();
    }
}
