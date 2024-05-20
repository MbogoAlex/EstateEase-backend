package com.propertymanagement.PropertyManagement.dao;

import com.propertymanagement.PropertyManagement.entity.Settings;

import java.util.Optional;

public interface SettingsDao {
    Settings findBySettingsKey(String settingsKey);
}
