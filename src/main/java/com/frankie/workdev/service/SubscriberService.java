package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.subscriber.request.CreateSubscriberDto;
import com.frankie.workdev.dto.subscriber.request.UpdateSubscriberDto;
import com.frankie.workdev.dto.subscriber.response.CreateSubscriberResponse;
import com.frankie.workdev.dto.subscriber.response.SubscriberListResponse;
import com.frankie.workdev.dto.subscriber.response.SubscriberResponse;
import com.frankie.workdev.dto.subscriber.response.UpdateSubscriberResponse;


public interface SubscriberService {
    ApiResponse<CreateSubscriberResponse> createSubscriber(CreateSubscriberDto subscriberDto);

    ApiResponse<SubscriberListResponse> getAllSubscriber(int pageNo, int pageSize,
                                                         String sortBy, String sortDir);

    ApiResponse<SubscriberResponse> getSubscriberById(String id);

    ApiResponse<UpdateSubscriberResponse> updateSubscriberById(String id,
                                                               UpdateSubscriberDto updateSubscriberDto);

    void deleteSubscriberById(String id);

    ApiResponse<SubscriberListResponse> searchSubscriberByEmail(String email, int pageNo,
                                                                int pageSize, String sortBy, String sortDir);
}
