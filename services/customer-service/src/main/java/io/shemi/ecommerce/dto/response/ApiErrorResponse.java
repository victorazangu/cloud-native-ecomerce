package io.shemi.ecommerce.dto.response;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public record ApiErrorResponse(
        String status,
        String message,
        String timestamp
) {
    public static ApiErrorResponse of(String status, String message) {
        String formattedTimestamp = DateTimeFormatter
                .ISO_OFFSET_DATE_TIME
                .withZone(ZoneOffset.UTC)
                .format(Instant.now());

        return new ApiErrorResponse(status, message, formattedTimestamp);
    }

    public static String getCurrentTimestamp() {
        return DateTimeFormatter
                .ISO_OFFSET_DATE_TIME
                .withZone(ZoneOffset.UTC)
                .format(Instant.now());
    }
}

