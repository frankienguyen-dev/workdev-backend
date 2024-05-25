package com.frankie.workdev.service;

import com.frankie.workdev.dto.apiResponse.ApiResponse;
import com.frankie.workdev.dto.subscriber.CreateSubscriberDto;
import com.frankie.workdev.dto.subscriber.SubscriberDto;
import com.frankie.workdev.dto.subscriber.SubscriberResponse;
import com.frankie.workdev.dto.subscriber.UpdateSubscriberDto;


public interface SubscriberService {
    ApiResponse<CreateSubscriberDto> createSubscriber(CreateSubscriberDto subscriberDto);

    ApiResponse<SubscriberResponse> getAllSubscriber(int pageNo, int pageSize,
                                                     String sortBy, String sortDir);

    ApiResponse<SubscriberDto> getSubsciberById(String id);

    ApiResponse<UpdateSubscriberDto> updateSubcriberById(String id,
                                                         UpdateSubscriberDto updateSubscriberDto);

    ApiResponse<String> deleteSubscriberById(String id);

    ApiResponse<SubscriberResponse> searchSubscriberByEmail(String email, int pageNo,
                                                            int pageSize, String sortBy, String sortDir);
}
