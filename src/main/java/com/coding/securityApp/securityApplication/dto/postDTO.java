package com.coding.securityApp.securityApplication.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class postDTO {
    private Long id;
    private String title;

    private String description;
}
