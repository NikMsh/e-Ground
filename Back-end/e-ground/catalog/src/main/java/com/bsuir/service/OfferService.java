package com.bsuir.service;

import com.bsuir.entity.Offer;

import java.util.List;
import java.util.UUID;

/**
 * Interface of offer service. Contains CRUD methods and methods for updating offer.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
public interface OfferService {
    Offer create(Offer offer);

    List<Offer> findAll();

    List<Offer> findAllByFilter(String category, String priceFrom, String priceTo);

    Offer findById(UUID id);

    Offer update(Offer offer);

    void delete(UUID id);

    Offer changeCategory(UUID offerId, String category);
}
