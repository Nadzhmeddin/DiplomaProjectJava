package ru.geekbrains.responseAnnotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.geekbrains.entity.Booking;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class BookingAPI {

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Бронирование не найдено", responseCode = "404", content = @Content(schema = @Schema(implementation = Void.class)))
    public @interface NotFoundResponse{
    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Внутренняя ошибка", responseCode = "500", content = @Content(schema = @Schema(implementation = Void.class)))
    public @interface ErrorFromServerResponse{

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Booking.class)))
    public @interface OKResponse {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Бронирование успешно создано", responseCode = "201", content = @Content(schema = @Schema(implementation = Booking.class)))
    public @interface CREATEDResponse {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Бронирование удалено", responseCode = "204", content = @Content(schema = @Schema(implementation = Booking.class)))
    public @interface NoContentResponse {

    }
}
