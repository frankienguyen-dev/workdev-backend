package com.frankie.workdev.dto.skill;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Skill DTO")
public class SkillDto {
    @Schema(description = "Skill name")
    @NotEmpty(message = "Name should not be empty")
    private String name;
}
