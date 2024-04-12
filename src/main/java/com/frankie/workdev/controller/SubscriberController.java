package com.frankie.workdev.controller;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.subscriber.CreateSubscriberDto;
import com.frankie.workdev.dto.subscriber.SubscriberDto;
import com.frankie.workdev.dto.subscriber.SubscriberResponse;
import com.frankie.workdev.dto.subscriber.UpdateSubscriberDto;
import com.frankie.workdev.service.SubscriberService;
import com.frankie.workdev.util.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/subscribers")
@AllArgsConstructor
public class SubscriberController {
    private SubscriberService subscriberService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateSubscriberDto>> createSubscriber
            (@RequestBody CreateSubscriberDto subscriberDto) {
        ApiResponse<CreateSubscriberDto> createSubscriber = subscriberService.createSubscriber(subscriberDto);
        return new ResponseEntity<>(createSubscriber, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<SubscriberResponse>> getAllSubscriber(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,
                    required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE,
                    required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY,
                    required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,
                    required = false) String sortDir
    ) {
        ApiResponse<SubscriberResponse> getAllSubscriber = subscriberService.getAllSubscriber(
                pageNo, pageSize, sortBy, sortDir
        );
        return new ResponseEntity<>(getAllSubscriber, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubscriberDto>> getSubscriberById(@PathVariable("id") String id) {
        ApiResponse<SubscriberDto> getSubscriberById = subscriberService.getSubsciberById(id);
        return new ResponseEntity<>(getSubscriberById, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<UpdateSubscriberDto>> updateSubscriberById(
            @PathVariable("id") String id, @RequestBody UpdateSubscriberDto updateSubscriberDto
    ) {
        ApiResponse<UpdateSubscriberDto> updateSubscriber = subscriberService
                .updateSubcriberById(id, updateSubscriberDto);
        return new ResponseEntity<>(updateSubscriber, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSubscriberById(@PathVariable("id") String id) {
        subscriberService.deleteSubscriberById(id);
        return new ResponseEntity<>("Subscriber deleted successfully", HttpStatus.OK);
    }
}
