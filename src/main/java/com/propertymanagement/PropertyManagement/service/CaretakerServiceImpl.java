package com.propertymanagement.PropertyManagement.service;

import com.propertymanagement.PropertyManagement.dao.CaretakerDao;
import com.propertymanagement.PropertyManagement.dao.PManagerDao;
import com.propertymanagement.PropertyManagement.dto.CaretakerAccountDTO;
import com.propertymanagement.PropertyManagement.dto.CaretakerLoginDTO;
import com.propertymanagement.PropertyManagement.dto.CaretakerResponseDTO;
import com.propertymanagement.PropertyManagement.entity.Caretaker;
import com.propertymanagement.PropertyManagement.entity.PManager;
import com.propertymanagement.PropertyManagement.entity.Role;
import com.propertymanagement.PropertyManagement.exception.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CaretakerServiceImpl implements CaretakerService{

    private CaretakerDao caretakerDao;
    private PManagerDao pManagerDao;
    @Autowired
    public CaretakerServiceImpl(
            CaretakerDao caretakerDao,
            PManagerDao pManagerDao
    ) {
        this.caretakerDao = caretakerDao;
        this.pManagerDao = pManagerDao;
    }
    @Transactional
    @Override
    public CaretakerResponseDTO createAccount(CaretakerAccountDTO caretakerAccountDTO) {
        System.out.println(caretakerAccountDTO);
        Role role = pManagerDao.getRoleById(caretakerAccountDTO.getRoleId());
        PManager pManager = pManagerDao.getPManagerById(caretakerAccountDTO.getpManagerId());
        Caretaker caretaker = new Caretaker();
        caretaker.setFullName(caretakerAccountDTO.getFullName());
        caretaker.setNationalIdOrPassportNumber(caretakerAccountDTO.getNationalIdOrPassportNumber());
        caretaker.setEmail(caretakerAccountDTO.getEmail());
        caretaker.setPhoneNumber(caretakerAccountDTO.getPhoneNumber());
        caretaker.setPassword(caretakerAccountDTO.getPassword());
        caretaker.setSalary(caretakerAccountDTO.getSalary());
        caretaker.setCaretakerAddedAt(LocalDateTime.now());
        caretaker.setActive(true);
        caretaker.getRoles().add(role);
        caretaker.setPManager(pManager);
        return mapCaretakerToCaretakerResponseDTO(caretakerDao.createAccount(caretaker));
    }

    @Override
    public CaretakerResponseDTO login(CaretakerLoginDTO caretakerLoginDTO) {
        String phoneNumber = caretakerLoginDTO.getPhoneNumber();
        String password = caretakerLoginDTO.getPassword();
        Caretaker caretaker = caretakerDao.findCaretakerByPhoneNumberAndPassword(phoneNumber, password);
        if(caretaker.getActive()) {
            return mapCaretakerToCaretakerResponseDTO(caretaker);
        } else {
            throw new DataNotFoundException("Invalid credentials");
        }
    }
    @Transactional
    @Override
    public CaretakerResponseDTO deregisterCaretaker(int id) {
        return mapCaretakerToCaretakerResponseDTO(caretakerDao.deregisterCaretaker(id));
    }

    public CaretakerResponseDTO mapCaretakerToCaretakerResponseDTO(Caretaker caretaker) {
        CaretakerResponseDTO caretakerResponseDTO = new CaretakerResponseDTO();
        CaretakerResponseDTO.PManagerDTO pManagerDTO = new CaretakerResponseDTO.PManagerDTO();

        pManagerDTO.setPManagerId(caretaker.getPManager().getpManagerId());
        pManagerDTO.setFullName(caretaker.getPManager().getFullName());
        pManagerDTO.setNationalIdOrPassportNumber(caretaker.getPManager().getNationalIdOrPassportNumber());
        pManagerDTO.setPhoneNumber(caretaker.getPManager().getPhoneNumber());
        pManagerDTO.setEmail(caretaker.getPManager().getEmail());

        caretakerResponseDTO.setCaretakerId(caretaker.getCaretakerId());
        caretakerResponseDTO.setFullName(caretaker.getFullName());
        caretakerResponseDTO.setNationalIdOrPassportNumber(caretaker.getNationalIdOrPassportNumber());
        caretakerResponseDTO.setPhoneNumber(caretaker.getPhoneNumber());
        caretakerResponseDTO.setEmail(caretaker.getEmail());
        caretakerResponseDTO.setCaretakerAddedAt(caretaker.getCaretakerAddedAt());
        caretakerResponseDTO.setActive(caretaker.getActive());
        caretakerResponseDTO.setPManager(pManagerDTO);
        return caretakerResponseDTO;
    }
}
