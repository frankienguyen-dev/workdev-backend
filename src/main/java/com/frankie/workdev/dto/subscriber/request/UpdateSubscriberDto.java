package com.frankie.workdev.dto.subscriber.request;

import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.subscriber.BaseSubscriber;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Schema(description = "Update subscriber DTO request")
public class UpdateSubscriberDto extends BaseSubscriber {
    @Schema(hidden = true)
    @Override
    public String getId() {
        return super.getId();
    }
}
