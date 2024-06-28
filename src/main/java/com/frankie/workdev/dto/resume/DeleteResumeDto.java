package com.frankie.workdev.dto.resume;

import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Delete resume DTO request")
public class DeleteResumeDto {

    @Schema(description = "Resume id")
    private String id;

    @Schema(description = "Resume deleted by")
    private JwtUserInfo deletedBy;

    @Schema(description = "Resume deleted at")
    private LocalDateTime deletedAt;

    @Schema(description = "Resume is deleted")
    private Boolean isDeleted;
}
