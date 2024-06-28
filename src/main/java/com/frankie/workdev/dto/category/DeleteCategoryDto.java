package com.frankie.workdev.dto.category;

import com.frankie.workdev.dto.user.response.JwtUserInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Delete category DTO request")
public class DeleteCategoryDto {

    @Schema(description = "Category Id")
    private String id;

    @Schema(description = "Category deleted by")
    private JwtUserInfo deletedBy;

    @Schema(description = "Category deleted at")
    private LocalDateTime deletedAt;

    @Schema(description = "Category is deleted")
    private Boolean isDeleted;
}
