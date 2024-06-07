package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.Amenity;
import com.propertymanagement.PropertyManagement.entity.AmenityImage;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AmenityDaoImpl implements AmenityDao{
    private final EntityManager entityManager;
    @Autowired
    public AmenityDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Amenity addAmenity(Amenity amenity) {
        entityManager.persist(amenity);
        return amenity;
    }

    @Override
    public Amenity updateAmenity(Amenity amenity) {
        entityManager.merge(amenity);
        return amenity;
    }

    @Override
    public Amenity getById(int id) {
        TypedQuery<Amenity> query = entityManager.createQuery("from Amenity where id = :id", Amenity.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public AmenityImage getImage(int id) {
        TypedQuery<AmenityImage> query = entityManager.createQuery("from AmenityImage where id = :id", AmenityImage.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Override
    public String deleteImage(int id) {
        Query query = entityManager.createQuery("delete from AmenityImage where id = :id");
        query.setParameter("id", id);
        int result = query.executeUpdate();
        return "Deleted "+result+" rows";
    }

    @Override
    public String deleteAmenity(int id) {
        Query query = entityManager.createQuery("delete from Amenity where id = :id");
        query.setParameter("id", id);

        int result = query.executeUpdate();
        return "Deleted "+result+" rows";
    }

    @Override
    public List<Amenity> getAllAmenities() {
        TypedQuery<Amenity> query = entityManager.createQuery("from Amenity", Amenity.class);
        return query.getResultList();
    }
}
