package com.gh.mirocodij.inventory.system.springinventorysystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Configuration
public class ConversionConfig {

    @Bean
    ConversionServiceFactoryBean conversionService(Set<Converter> converters) {
        var bean = new ConversionServiceFactoryBean();
        bean.setConverters(converters);
        return bean;
    }
}
