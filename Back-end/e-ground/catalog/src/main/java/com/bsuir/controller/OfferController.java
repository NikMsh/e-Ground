package com.bsuir.controller;

import com.bsuir.dto.OfferDto;
import com.bsuir.entity.Category;
import com.bsuir.entity.Offer;
import com.bsuir.repository.CategoryRepository;
import com.bsuir.service.OfferService;
import com.bsuir.service.util.OfferConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * Class of Offer REST Controller. Contains CRUD methods and methods for updating offer.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api/v1/catalog/offers")
public class OfferController {
    /**
     * Field of Offer Service.
     */
    private final OfferService offerService;

    private final CategoryRepository categoryRepository;

    private final OfferConverter offerConverter;

    /**
     * Constructor that accepts objects of OfferService, TagService, CategoryService classes.
     *
     * @param offerService       object of OfferService class
     * @param categoryRepository
     * @param offerConverter
     */
    @Autowired
    public OfferController(OfferService offerService, CategoryRepository categoryRepository, OfferConverter offerConverter) {
        this.offerService = offerService;
        this.categoryRepository = categoryRepository;
        this.offerConverter = offerConverter;
    }

    /**
     * Method that converts DTO to class object and create it.
     *
     * @param offerDto data transfer object
     * @return created object of Offer class
     */
    @PostMapping
    public OfferDto create(@Validated @RequestBody OfferDto offerDto) {
        Offer offerTemp = offerConverter.toOffer(offerDto);
        Category category = categoryRepository.findFirstByName(offerDto.getCategory());
        if (category == null || category.getName().equals(offerTemp.getCategory().getName())) {
            Category categorySave = categoryRepository.save(new Category(offerDto.getCategory()));
            offerTemp.setCategory(categorySave);
        } else {
            offerTemp.setCategory(category);
        }

        return offerConverter.toOfferDto(offerService.create(offerTemp));
    }

    /**
     * Method that save updated object.
     *
     * @param offerDto updated Offer that needs to save
     * @return updated and saved offer
     */
    @PutMapping
    public OfferDto update(@Validated @RequestBody OfferDto offerDto) {
        Offer offerTemp = offerConverter.toOffer(offerDto);
        return offerConverter.toOfferDto(offerService.update(offerTemp));
    }

    /**
     * Method that delete object.
     *
     * @param id object that needs to delete
     */
    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable("id") UUID id) {
        offerService.delete(id);
    }

    /**
     * Method that finds Offer object by Long.
     *
     * @param id parameter to be searched
     * @return List of founded objects
     */
    @GetMapping(path = "/{id}")
    public OfferDto getById(@PathVariable("id") UUID id) {
        return offerConverter.toOfferDto(offerService.findById(id));
    }

    /**
     * Method that finds all objects.
     *
     * @return founded objects
     */
    @GetMapping
    public List<OfferDto> getAll() {
        List<Offer> offersTemp = offerService.findAll();
        return offerConverter.toOffersDto(offersTemp);
    }

    /**
     * Method that changes Category to Offer.
     *
     * @param offerId      Offer Long
     * @param categoryName Category to change
     * @return updated Offer
     */
    @PutMapping(path = "/{offerId}/categories/{categoryName}")
    public OfferDto changeCategory(@PathVariable("offerId") UUID offerId, @PathVariable("categoryName") String categoryName) {
        return offerConverter.toOfferDto(offerService.changeCategory(offerId, categoryName));
    }

    @GetMapping(path = "/filter")
    public List<OfferDto> getAllByFilter(@RequestParam(value = "category", required = false) String category,
                                         @RequestParam(value = "priceFrom", required = false) String priceFrom,
                                         @RequestParam(value = "priceTo", required = false) String priceTo) {
        List<Offer> offersTemp = offerService.findAllByFilter(category, priceFrom, priceTo);

        return offerConverter.toOffersDto(offersTemp);
    }
}
