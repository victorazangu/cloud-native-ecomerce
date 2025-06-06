package io.shemi.ecommerce.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record CustomerResponse(
        String id,
        String firstName,
        String lastName,
        String username,
        String email,
        String phoneNumber,
        Boolean emailVerified,
        AddressResponse address,
        String preferredLanguage,
        String preferredCurrency,
        Boolean isActive,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        String profileImageUrl,
        String referralCode
) {
}
