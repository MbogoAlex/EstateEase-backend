package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.dto.CaretakerAccountDTO;
import com.propertymanagement.PropertyManagement.dto.CaretakerLoginDTO;
import com.propertymanagement.PropertyManagement.entity.Caretaker;

public interface CaretakerDao {
    Caretaker createAccount(Caretaker caretaker);

    Caretaker findCaretakerById(int id);

    Caretaker findCaretakerByPhoneNumberAndPassword(String phoneNumber, String password);

    Caretaker deregisterCaretaker(int id);
}
