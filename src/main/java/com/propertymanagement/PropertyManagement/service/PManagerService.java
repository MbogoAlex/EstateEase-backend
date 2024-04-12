package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dto.*;
import com.propertymanagement.PropertyManagement.dto.pManagerResponse.PManagerResponseDTO;
import com.propertymanagement.PropertyManagement.entity.PManager;
import com.propertymanagement.PropertyManagement.entity.RentPayment;
import java.util.List;

public interface PManagerService {
    // add pManager
    PManagerResponseDTO addNewPManager(PManagerDTO pManagerDTO);

    // updatePManager

    PManagerResponseDTO updatePManager(PManagerDTO pManagerDTO, int pManagerId);

    // archivePManager

    PManagerResponseDTO archivePManager(int pManagerId);

    //PManager Login
    PManagerResponseDTO pManagerLogin(PManagerLoginDTO pManagerLoginDTO);

    RentPaymentOverviewDTO getRentPaymentOverview(String month, String year);

    List<RentPaymentDetailsDTO> getRentPaymentDetailedInfo(String month, String year, Integer rooms, String roomName, String tenantName);

    List<DetailedRentPaymentInfoDTO> getDetailedRentPayments(String month, String year, String roomName, Integer rooms, String tenantName, Boolean rentPaymentStatus);

}
