package com.frankie.workdev.dto.subscriber.request;

import com.frankie.workdev.dto.subscriber.BaseSubscriber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Create subscriber DTO request")
public class CreateSubscriberDto extends BaseSubscriber {
    @Schema(hidden = true)
    @Override
    public String getId() {
        return super.getId();
    }
}
