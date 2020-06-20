package com.webproject.form;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class PresentationForm {

    @NotBlank
    @Size(min=2, max=50, message = "Името трябва да бъде с дължина от 2 до 50 символа")
    private String name;

    @NotNull(message = "Трябва да качите файл")
    private MultipartFile zipFile;

    @NotBlank(message = "Презентациите трябва да имат поне един таг")
    private String tags;

}
