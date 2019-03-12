package com.bsuir.service.util.impl;

import com.bsuir.dto.OfferDto;
import com.bsuir.entity.Category;
import com.bsuir.entity.Offer;
import com.bsuir.service.util.OfferConverter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DefaultOfferConverter implements OfferConverter {
    @Override
    public List<OfferDto> toOffersDto(List<Offer> offers) {
        List<OfferDto> offersDto = new ArrayList<>();
        if (offers != null) {
            offers.forEach(offer -> offersDto.add(toOfferDto(offer)));
        }
        return offersDto;
    }

    @Override
    public List<Offer> toOffers(List<OfferDto> offersDto) {
        List<Offer> offers = new ArrayList<>();
        if (offersDto != null) {
            offersDto.forEach(offerDto -> offers.add(toOffer(offerDto)));
        }
        return offers;
    }

    @Override
    public Offer toOffer(OfferDto offerDto) {
        Offer offer = new Offer();

        if (offerDto != null) {
            offer.setId(offerDto.getId());
            offer.setName(offerDto.getName());
            offer.setPrice(offerDto.getPrice());

            Category category = new Category();
            category.setName(offerDto.getName());
            offer.setCategory(category);
        }
        return offer;
    }

    @Override
    public OfferDto toOfferDto(Offer offer) {
        OfferDto offerDto = new OfferDto();
        if (offer != null) {
            offerDto.setId(offer.getId());
            offerDto.setName(offer.getName());
            offerDto.setCategory(offer.getCategory().getName());
            offerDto.setPrice(offer.getPrice());
        }
        return offerDto;
    }
}
