package com.frankie.workdev.dto.apiResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private String message;
    private int statusCode;
    private T data;

    public static <T> ApiResponse<T> success(String message, HttpStatus statusCode, T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(message);
        apiResponse.setStatusCode(statusCode.value());
        apiResponse.setData(data);
        return apiResponse;
    }

    public static <T> ApiResponse<T> error(String message, HttpStatus statusCode, T data) {
        ApiResponse<T> apiResponse = new ApiResponse<>();
        apiResponse.setMessage(message);
        apiResponse.setStatusCode(statusCode.value());
        apiResponse.setData(data);
        return apiResponse;
    }
}
