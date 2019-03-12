package com.bsuir.service.util;

import com.bsuir.dto.OfferDto;
import com.bsuir.entity.Offer;

import java.util.List;

public interface OfferConverter {
    List<OfferDto> toOffersDto(List<Offer> offers);

    Offer toOffer(OfferDto offerDTO);

    OfferDto toOfferDto(Offer offer);

    List<Offer> toOffers(List<OfferDto> productsDTO);
}
