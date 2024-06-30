package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.subscriber.request.CreateSubscriberDto;
import com.frankie.workdev.dto.subscriber.request.UpdateSubscriberDto;
import com.frankie.workdev.dto.subscriber.response.CreateSubscriberResponse;
import com.frankie.workdev.dto.subscriber.response.SubscriberListResponse;
import com.frankie.workdev.dto.subscriber.response.SubscriberResponse;
import com.frankie.workdev.dto.subscriber.response.UpdateSubscriberResponse;
import com.frankie.workdev.service.SubscriberService;
import com.frankie.workdev.util.AppConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "CRUD operations for subscriber",
        description = "CRUD operations for subscriber controller: Create new subscriber, " +
                "Get all subscribers, Get subscriber by id, Update subscriber by id, " +
                "Soft delete subscriber by id, Search subscriber.")
@RestController
@RequestMapping("/api/v1/subscribers")
@AllArgsConstructor
public class SubscriberController {
    private SubscriberService subscriberService;

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Create new subscriber",
            description = "Create new subscriber controller is used to create " +
                    "new subscriber in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "201",
            description = "Subscriber created successfully"
    )
    @PostMapping
    public ResponseEntity<ApiResponse<CreateSubscriberResponse>> createSubscriber
            (@RequestBody @Valid CreateSubscriberDto subscriberDto) {
        ApiResponse<CreateSubscriberResponse> createSubscriber = subscriberService.createSubscriber(subscriberDto);
        return new ResponseEntity<>(createSubscriber, HttpStatus.CREATED);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get all subscribers",
            description = "Get all subscribers controller is used to get all subscribers in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Get all subscribers successfully"
    )
    @GetMapping
    public ResponseEntity<ApiResponse<SubscriberListResponse>> getAllSubscriber(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<SubscriberListResponse> getAllSubscriber = subscriberService.getAllSubscriber(
                pageNo, pageSize, sortBy, sortDir
        );
        return new ResponseEntity<>(getAllSubscriber, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Get subscriber by id",
            description = "Get subscriber by id controller is used to get subscriber " +
                    "by id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Get subscriber by id successfully"
    )
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubscriberResponse>> getSubscriberById(@PathVariable("id") String id) {
        ApiResponse<SubscriberResponse> getSubscriberById = subscriberService.getSubscriberById(id);
        return new ResponseEntity<>(getSubscriberById, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Update subscriber by id",
            description = "Update subscriber by id controller is used to update subscriber " +
                    "by id in the database. "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Update subscriber by id successfully"
    )
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateSubscriberResponse>> updateSubscriberById(
            @PathVariable("id") String id, @RequestBody @Valid UpdateSubscriberDto updateSubscriberDto
    ) {
        ApiResponse<UpdateSubscriberResponse> updateSubscriber = subscriberService
                .updateSubscriberById(id, updateSubscriberDto);
        return new ResponseEntity<>(updateSubscriber, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Soft delete subscriber by id",
            description = "Soft delete subscriber by id controller is used to soft delete "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Soft delete subscriber by id successfully"
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubscriberById(@PathVariable("id") String id) {
        subscriberService.deleteSubscriberById(id);
        return new ResponseEntity<>("Subscriber deleted successfully", HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(
            summary = "Search subscriber by email",
            description = "Search subscriber by email controller is used to search "
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Search subscriber by email successfully"
    )
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<SubscriberListResponse>> searchSubscriber(
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<SubscriberListResponse> searchSubscriber = subscriberService.searchSubscriberByEmail(
                email, pageNo, pageSize, sortBy, sortDir
        );
        return new ResponseEntity<>(searchSubscriber, HttpStatus.OK);
    }
}
