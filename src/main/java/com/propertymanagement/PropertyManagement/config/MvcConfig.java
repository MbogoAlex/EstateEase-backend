package com.propertymanagement.PropertyManagement.config;

import com.propertymanagement.PropertyManagement.dao.SettingsDao;
import com.propertymanagement.PropertyManagement.entity.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class MvcConfig implements WebMvcConfigurer {
    private SettingsDao settingsDao;
    @Autowired
    public MvcConfig(SettingsDao settingsDao) {
        this.settingsDao = settingsDao;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        Settings result = settingsDao.findBySettingsKey("imagePath");
        String path = result.getValue();

        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" +path);
    }
}
