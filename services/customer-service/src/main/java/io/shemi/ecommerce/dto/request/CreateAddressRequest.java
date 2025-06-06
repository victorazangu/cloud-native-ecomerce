package io.shemi.ecommerce.dto.request;

import io.shemi.ecommerce.entity.Address;
import jakarta.validation.constraints.NotBlank;

public record CreateAddressRequest(
        @NotBlank(message = "Street is required")
        String street,

        @NotBlank(message = "City is required")
        String city,

        @NotBlank(message = "State is required")
        String state,

        @NotBlank(message = "Postal code is required")
        String postalCode,

        @NotBlank(message = "Country is required")
        String country
) {
    public Address toAddressEntity() {
        return Address.builder()
                .street(this.street)
                .city(this.city)
                .state(this.state)
                .postalCode(this.postalCode)
                .country(this.country)
                .build();
    }
}
