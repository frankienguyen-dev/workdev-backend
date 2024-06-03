package com.frankie.workdev.dto.user;

import com.frankie.workdev.dto.upload.FileUploadDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResumeInfo {
    private String id;
    private String fullName;
    private String email;
    private String address;
    private FileUploadDto avatar;
    private String education;
    private String experience;
    private String phoneNumber;
    private String biography;
    private String coverLetter;
    private String gender;
    private String title;
    private int age;
}
