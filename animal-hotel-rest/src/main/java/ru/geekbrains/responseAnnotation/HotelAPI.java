package ru.geekbrains.responseAnnotation;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import ru.geekbrains.entity.Hotel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class HotelAPI {

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Отель не найден", responseCode = "404", content = @Content(schema = @Schema(implementation = Void.class)))
    public @interface NotFoundResponse{
    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Внутренняя ошибка", responseCode = "500", content = @Content(schema = @Schema(implementation = Void.class)))
    public @interface ErrorFromServerResponse{

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = Hotel.class)))
    public @interface OKResponse {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Отель успешно создан", responseCode = "201", content = @Content(schema = @Schema(implementation = Hotel.class)))
    public @interface CREATEDResponse {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @ApiResponse(description = "Отель удален", responseCode = "204", content = @Content(schema = @Schema(implementation = Hotel.class)))
    public @interface NoContentResponse {

    }
}
