package com.abhinav.College.Discovery.Platform.DTO;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PredictorRequest {

    @NotBlank(message = "Exam name is required")
    private String exam;

    @NotNull(message = "Rank value is required")
    @Min(value = 1, message = "Rank must be a positive integer starting from 1")
    private Integer rank;

    @NotBlank(message = "Category field is mandatory")
    private String category = "General";
}