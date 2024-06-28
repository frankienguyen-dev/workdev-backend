package com.frankie.workdev.dto.subscriber.response;

import com.frankie.workdev.dto.skill.SkillDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Subscriber Response")
public class SubscriberResponse {

    @Schema(description = "Subscriber ID")
    private String id;

    @Schema(description = "Subscriber Name")
    private String name;

    @Schema(description = "Subscriber Email")
    private String email;

    @Schema(description = "Subscriber Skills")
    private List<SkillDto> skills;
}
