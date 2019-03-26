package com.bsuir.sdtt.configuration;

import com.bsuir.sdtt.dto.OfferDto;
import com.bsuir.sdtt.entity.Category;
import com.bsuir.sdtt.entity.Offer;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        PropertyMap<OfferDto, Offer> dtoCustomerPropertyMap = new PropertyMap<OfferDto, Offer>() {
            protected void configure() {
                map().setCategory(new Category());
                map().getCategory().setName(source.getCategory());
            }
        };

        PropertyMap<Offer, OfferDto> customerPropertyMap = new PropertyMap<Offer, OfferDto>() {
            protected void configure() {
               map().setCategory(source.getCategory().getName());
            }
        };

        ModelMapper modelMapper = new ModelMapper();

        modelMapper.addMappings(dtoCustomerPropertyMap);
        modelMapper.addMappings(customerPropertyMap);

        return modelMapper;
    }

}

