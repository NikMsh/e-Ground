package com.bsuir.sdtt.service.impl;

import com.bsuir.sdtt.entity.Category;
import com.bsuir.sdtt.entity.Offer;
import com.bsuir.sdtt.exception.EntityNotFoundException;
import com.bsuir.sdtt.repository.CategoryRepository;
import com.bsuir.sdtt.repository.OfferRepository;
import com.bsuir.sdtt.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Class of offer service that allows you to work with offers and implements OfferService.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Service
@Transactional
public class DefaultOfferService implements OfferService {
    private final OfferRepository offerRepository;

    private final CategoryRepository categoryRepository;

    @Autowired
    public DefaultOfferService(OfferRepository offerRepository,
                               CategoryRepository categoryRepository) {
        this.offerRepository = offerRepository;
        this.categoryRepository = categoryRepository;
    }

    /**
     * Method that save object in database.
     *
     * @param offer object that needs to save
     * @return saved object of Offer class
     */
    @Override
    public Offer create(Offer offer) {
        return offerRepository.save(offer);
    }

    /**
     * Method that finds all objects in database.
     *
     * @return founded objects
     */
    @Override
    public List<Offer> findAll() {
        Iterable<Offer> saveOffer = offerRepository.findAll();
        List<Offer> createdOffers = new ArrayList<>();
        for (Offer offer : saveOffer) {
            createdOffers.add(offer);
        }
        return createdOffers;
    }

    @Override
    public List<Offer> findAllByFilter(String category, String priceFrom,
                                       String priceTo) {
        Iterable<Offer> saveOffer;

        Category foundCategory = null;

        double priceFromTemp;

        double priceToTemp;

        List<Offer> foundOffers = new ArrayList<>();

        if (category != null) {
            foundCategory = categoryRepository.findFirstByName(category);
        }

        if (priceFrom != null && Double.valueOf(priceFrom) >= 0) {
            priceFromTemp = Double.valueOf(priceFrom);
        } else {
            priceFromTemp = 0;
        }

        if (priceTo != null && Double.valueOf(priceTo) > priceFromTemp) {
            priceToTemp = Double.valueOf(priceTo);
        } else {
            priceToTemp = Double.MAX_VALUE;
        }

        if (category != null) {
            saveOffer = offerRepository.findAllByCategory(foundCategory);
        } else {
            saveOffer = offerRepository.findAll();
        }

        for (Offer offer : saveOffer) {
            if (offer.getPrice() >= priceFromTemp
                    && offer.getPrice() < priceToTemp) {
                foundOffers.add(offer);
            }
        }

        return foundOffers;
    }

    /**
     * Method that finds Offer object by Long in database.
     *
     * @param id parameter to be searched
     * @return list of founded objects
     */
    @Override
    public Offer findById(UUID id) {
        return offerRepository.findById(id)
                .orElseThrow(NullPointerException::new);
    }

    /**
     * Method that save updated object in database.
     *
     * @param offer updated offer that needs to save
     * @return updated and saved offer
     */
    @Override
    public Offer update(Offer offer) {
        return create(offer);
    }

    /**
     * Method that delete object in database.
     *
     * @param id parameter to be searched
     */
    @Override
    public void delete(UUID id) {
        try {
            offerRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    /**
     * Method that changes Category to Offer.
     *
     * @param offerId  Offer class object
     * @param category Category class object
     * @return updated Offer
     */
    @Override
    public Offer changeCategory(UUID offerId, String category) {
        Offer offer = offerRepository
                .findById(offerId)
                .orElseThrow(NullPointerException::new);
        Category categoryByName = categoryRepository.findFirstByName(category);
        offer.setCategory(categoryByName);
        Offer savedOffer = offerRepository.save(offer);
        return savedOffer;
    }
}
