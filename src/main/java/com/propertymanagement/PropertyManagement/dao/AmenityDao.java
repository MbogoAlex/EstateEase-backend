package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.Amenity;
import com.propertymanagement.PropertyManagement.entity.AmenityImage;
import java.util.List;

public interface AmenityDao {
    Amenity addAmenity(Amenity amenity);

    Amenity updateAmenity(Amenity amenity);

    Amenity getById(int id);

    AmenityImage getImage(int id);

    String deleteImage(int id);
    String deleteAmenity(int id);

    List<Amenity> getAllAmenities();
}
