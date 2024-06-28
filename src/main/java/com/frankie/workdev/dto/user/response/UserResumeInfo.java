package com.frankie.workdev.dto.user.response;

import com.frankie.workdev.dto.upload.FileUploadDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "User Resume Info")
public class UserResumeInfo {

    @Schema(description = "User Id")
    private String id;

    @Schema(description = "User full name")
    private String fullName;

    @Schema(description = "User email")
    private String email;

    @Schema(description = "User address")
    private String address;

    @Schema(description = "User avatar")
    private FileUploadDto avatar;

    @Schema(description = "User education")
    private String education;

    @Schema(description = "User experience")
    private String experience;

    @Schema(description = "User phone number")
    private String phoneNumber;

    @Schema(description = "User biography")
    private String biography;

    @Schema(description = "User cover letter")
    private String coverLetter;

    @Schema(description = "User gender")
    private String gender;

    @Schema(description = "User title")
    private String title;

    @Schema(description = "User age")
    private int age;
}
