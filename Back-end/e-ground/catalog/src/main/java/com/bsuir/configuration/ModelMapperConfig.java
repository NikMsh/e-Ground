package com.bsuir.configuration;

import com.bsuir.dto.OfferDto;
import com.bsuir.entity.Offer;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        PropertyMap<OfferDto, Offer> dtoOfferPropertyMap = new PropertyMap<OfferDto, Offer>() {
            protected void configure() {
                map().getCategory().setName(source.getCategory());
            }
        };

        PropertyMap<Offer, OfferDto> customerPropertyMap = new PropertyMap<Offer, OfferDto>() {
            protected void configure() {
                map().setCategory(source.getCategory().getName());
            }
        };

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(dtoOfferPropertyMap);
        modelMapper.addMappings(customerPropertyMap);

        return modelMapper;
    }
}

