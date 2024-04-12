package com.frankie.workdev.dto.subscriber;

import com.frankie.workdev.dto.skill.SkillDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSubscriberDto {
    private String id;
    private String name;
    private String email;
    private List<SkillDto> skills;
}
