package com.frankie.workdev.dto.subscriber.request;

import com.frankie.workdev.dto.skill.SkillDto;
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
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Update subscriber DTO request")
public class UpdateSubscriberDto {

    @Schema(description = "Subscriber Id", hidden = true)
    private String id;

    @Schema(description = "Subscriber name")
    @NotEmpty(message = "Name should not be empty")
    private String name;

    @Schema(description = "Subscriber email")
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @Schema(description = "Subscriber skills")
    @NotEmpty(message = "Skills should not be empty")
    private List<SkillDto> skills;
}
