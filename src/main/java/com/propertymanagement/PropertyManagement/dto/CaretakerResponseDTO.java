package com.propertymanagement.PropertyManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaretakerResponseDTO {
    private int caretakerId;
    private String fullName;
    private String nationalIdOrPassportNumber;
    private String phoneNumber;
    private String email;
    private LocalDateTime caretakerAddedAt;
    private Boolean active;
    private PManagerDTO pManager;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PManagerDTO {
        private int pManagerId;
        private String fullName;
        private String nationalIdOrPassportNumber;
        private String phoneNumber;
        private String email;
    }
}

