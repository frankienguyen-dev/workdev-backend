package com.frankie.workdev.dto.subscriber.response;

import com.frankie.workdev.dto.apiResponse.MetaData;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Subscriber Response")
public class SubscriberListResponse {

    @Schema(description = "Response metadata")
    private MetaData meta;

    @Schema(description = "Subscriber data")
    private List<SubscriberResponse> data;
}
