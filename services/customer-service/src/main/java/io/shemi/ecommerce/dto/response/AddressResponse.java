package io.shemi.ecommerce.dto.response;

import lombok.Builder;

@Builder
public record AddressResponse(
        String street,
        String city,
        String state,
        String postalCode,
        String country
) {
}
