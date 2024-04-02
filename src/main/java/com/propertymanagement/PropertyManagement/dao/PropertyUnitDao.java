package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.PropertyUnit;
import com.propertymanagement.PropertyManagement.entity.RentPayment;

import java.util.List;
public interface PropertyUnitDao {
    // add property
    PropertyUnit addNewProperty(PropertyUnit propertyUnit);

    // update property
    PropertyUnit updateProperty(PropertyUnit propertyUnit);

    // delete property
    String deleteProperty(int id);

    // get all properties
    List<PropertyUnit> getAllProperties();

    // get property by id
    PropertyUnit getPropertyByPropertyId(int propertyId);

    // archive property
    PropertyUnit archiveProperty(PropertyUnit propertyUnit);

    // fetch property by property number or name
    PropertyUnit fetchPropertyByNumberOrId(String propertyNumOrName);

    // fetch all occupied units
    List<PropertyUnit> fetchAllOccupiedUnits();


}
