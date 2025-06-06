package io.shemi.ecommerce.dto.request;

import jakarta.validation.Valid;

public record UpdateCustomerRequest(
        String firstName,
        String lastName,
        String phoneNumber,

        @Valid
        CreateAddressRequest address,
        String preferredLanguage,
        String preferredCurrency,
        String profileImageUrl
) {
}
