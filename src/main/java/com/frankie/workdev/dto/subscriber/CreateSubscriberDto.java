package com.frankie.workdev.dto.subscriber;

import com.frankie.workdev.dto.skill.SkillDto;
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
    private String email;
    private String name;
    private List<SkillDto> skills;
}
