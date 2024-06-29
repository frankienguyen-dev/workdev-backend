package com.frankie.workdev.dto.subscriber.response;

import com.frankie.workdev.dto.skill.SkillDto;
import com.frankie.workdev.dto.subscriber.BaseSubscriber;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@Schema(description = "Subscriber Response")
public class SubscriberResponse extends BaseSubscriber {
}
