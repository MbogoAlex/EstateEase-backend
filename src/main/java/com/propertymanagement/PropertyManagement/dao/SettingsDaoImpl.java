package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.Settings;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SettingsDaoImpl implements SettingsDao{
    private EntityManager entityManager;
    @Autowired
    public SettingsDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    @Override
    public Settings findBySettingsKey(String settingsKey) {
        TypedQuery<Settings> query = entityManager.createQuery("from Settings where settingsKey =:settingsKey", Settings.class);
        query.setParameter("settingsKey", settingsKey);
        return query.getSingleResult();
    }
}
