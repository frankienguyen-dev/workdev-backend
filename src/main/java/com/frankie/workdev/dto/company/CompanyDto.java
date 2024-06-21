package com.frankie.workdev.dto.company;

import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.JwtUserInfo;
import com.frankie.workdev.dto.user.UserInfoDto;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDto {
    private String id;

    @NotEmpty(message = "Name should not be empty")
    private String name;
    private String description;
    private String companyType;
    private String companyBenefit;
    private String address;
    private String email;
    private String phoneNumber;
    private String website;
    private Long teamSize;
    private LocalDateTime foundedDate;
    private FileUploadDto logo;
    private FileUploadDto banner;
    private List<JwtUserInfo> users;
    private JwtUserInfo createdBy;
    private LocalDateTime createdAt;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;
    private JwtUserInfo deletedBy;
    private LocalDateTime deletedAt;
    private Boolean isDeleted;
}
