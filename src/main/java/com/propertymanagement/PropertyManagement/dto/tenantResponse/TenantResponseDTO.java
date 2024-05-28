package com.propertymanagement.PropertyManagement.dto.tenantResponse;

import com.propertymanagement.PropertyManagement.dto.MeterReadingDTO;
import com.propertymanagement.PropertyManagement.dto.RentPaymentDTO;
import com.propertymanagement.PropertyManagement.dto.RentPaymentDetailsDTO;
import com.propertymanagement.PropertyManagement.dto.WaterMeterDataDTO;
import com.propertymanagement.PropertyManagement.entity.RentPayment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TenantResponseDTO {
    private int tenantId;
    private String fullName;
    private String nationalIdOrPassportNumber;
    private String phoneNumber;
    private String email;
    private String tenantAddedAt;
    private Boolean tenantActive;
    private TenantPropertyDTO propertyUnit;
    private List<PaymentInfoDTO> paymentInfo = new ArrayList<>();
    private List<WaterMeterDataDTO> waterMeterDataDTOList = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PaymentInfoDTO {
        private int rentPaymentTblId;
        private LocalDate dueDate;
        private Month month;
        private Double monthlyRent;
        private Double paidAmount;
        private LocalDateTime paidAt;
        private Boolean paidLate;
        private Long daysLate;
        private Boolean rentPaymentStatus;
        private Boolean penaltyActive;
        private Double penaltyPerDay;
        private String transactionId;
        private Year year;
        private String propertyNumberOrName;
        private Integer numberOfRooms;
        private Integer tenantId;
        private String email;
        private String fullName;
        private String nationalIdOrPassport;
        private String phoneNumber;
        private LocalDateTime tenantAddedAt;
        private Boolean tenantActive;
    }
}
