package com.frankie.workdev.dto.upload;

import com.frankie.workdev.dto.apiResponse.MetaData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadResponse {
    private MetaData meta;
    private List<FileUploadDto> data;
}
