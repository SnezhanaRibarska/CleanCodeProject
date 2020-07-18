package com.webproject.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PresentationForm {

    @NotBlank
    @Size(min=2, max=50, message = "The name should be 2 to 50 characters long")
    private String name;

    @NotNull(message = "You should upload a file")
    private MultipartFile zipFile;

    @NotBlank(message = "Presentations must have at least one tag")
    private String tags;

}
