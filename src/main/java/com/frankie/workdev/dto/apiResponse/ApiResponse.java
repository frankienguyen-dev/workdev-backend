package com.frankie.workdev.dto.apiResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "API response")
public class ApiResponse<T> {

    @Schema(description = "Response message")
    private String message;

    @Schema(description = "Response status code")
    private int statusCode;

    @Schema(description = "Response data")
    private T data;

    @Schema(description = "Response success")
    public static <T> ApiResponse<T> success(String message, HttpStatus statusCode, T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(message);
        apiResponse.setStatusCode(statusCode.value());
        apiResponse.setData(data);
        return apiResponse;
    }

    @Schema(description = "Response error")
    public static <T> ApiResponse<T> error(String message, HttpStatus statusCode, T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(message);
        apiResponse.setStatusCode(statusCode.value());
        apiResponse.setData(data);
        return apiResponse;
    }
}
