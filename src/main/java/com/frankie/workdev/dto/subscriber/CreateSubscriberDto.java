package com.frankie.workdev.dto.subscriber;

import com.frankie.workdev.dto.skill.SkillDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSubscriberDto {
    private String id;

    @Email(message = "Email should be valid")
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    @NotEmpty(message = "Skills should not be empty")
    private List<SkillDto> skills;
}
