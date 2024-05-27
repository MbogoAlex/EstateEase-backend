package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dto.CaretakerAccountDTO;
import com.propertymanagement.PropertyManagement.dto.CaretakerResponseDTO;
import com.propertymanagement.PropertyManagement.dto.CaretakerLoginDTO;
import com.propertymanagement.PropertyManagement.entity.Caretaker;

public interface CaretakerService {
    CaretakerResponseDTO createAccount(CaretakerAccountDTO caretakerAccountDTO);
    CaretakerResponseDTO login(CaretakerLoginDTO caretakerLoginDTO);

    CaretakerResponseDTO deregisterCaretaker(int id);
}
