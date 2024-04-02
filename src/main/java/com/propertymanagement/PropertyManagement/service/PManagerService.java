package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dto.PManagerDTO;
import com.propertymanagement.PropertyManagement.dto.PManagerLoginDTO;
import com.propertymanagement.PropertyManagement.dto.RentPaymentOverviewDTO;
import com.propertymanagement.PropertyManagement.dto.pManagerResponse.PManagerResponseDTO;
import com.propertymanagement.PropertyManagement.entity.PManager;

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
}
