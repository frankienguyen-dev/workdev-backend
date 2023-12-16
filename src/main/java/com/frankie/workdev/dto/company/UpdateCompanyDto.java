package com.frankie.workdev.dto.company;

import com.frankie.workdev.dto.upload.FileUploadDto;
import com.frankie.workdev.dto.user.JwtUserInfo;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCompanyDto {
    private String id;
    @NotEmpty(message = "Name should not be empty")
    private String name;
    @NotEmpty(message = "Description should not be empty")
    private String description;
    @NotEmpty(message = "Address should not be empty")
    private String address;
//    @NotEmpty(message = "Logo should not be empty")
    private FileUploadDto logo;
    private JwtUserInfo updatedBy;
    private LocalDateTime updatedAt;

}
