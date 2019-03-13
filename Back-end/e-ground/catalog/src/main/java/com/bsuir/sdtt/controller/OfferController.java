package com.bsuir.sdtt.controller;

import com.bsuir.sdtt.dto.OfferDto;
import com.bsuir.sdtt.entity.Category;
import com.bsuir.sdtt.entity.Offer;
import com.bsuir.sdtt.repository.CategoryRepository;
import com.bsuir.sdtt.service.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    /**
     * Field of Category Repository.
     */
    private final CategoryRepository categoryRepository;

    /**
     * Field of Model Mapper converter.
     */
    private final ModelMapper modelMapper;

    @Autowired
    public OfferController(OfferService offerService,
                           CategoryRepository categoryRepository,
                           ModelMapper modelMapper) {
        this.offerService = offerService;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper; }

    /**
     * Method that converts DTO to class object and create it.
     *
     * @param offerDto data transfer object
     * @return created object of Offer class
     */
    @PostMapping
    public OfferDto create(@Validated @RequestBody OfferDto offerDto) {
        Offer offerTemp = new Offer();
        modelMapper.map(offerDto, offerTemp);
        Category category = categoryRepository
                .findFirstByName(offerDto.getCategory());
        if (category == null || category.getName()
                .equals(offerTemp.getCategory().getName())) {
            Category categorySave = categoryRepository
                    .save(new Category(offerDto.getCategory()));
            offerTemp.setCategory(categorySave);
        } else {
            offerTemp.setCategory(category);
        }
        OfferDto offerDtoTemp = new OfferDto();
        modelMapper.map(offerService.create(offerTemp), offerDtoTemp);
        return offerDtoTemp;
    }

    /**
     * Method that save updated object.
     *
     * @param offerDto updated Offer that needs to save
     * @return updated and saved offer
     */
    @PutMapping
    public OfferDto update(@Validated @RequestBody OfferDto offerDto) {
        Offer offerTemp = new Offer();
        modelMapper.map(offerDto, offerTemp);
        OfferDto offerDtoTemp = new OfferDto();
        modelMapper.map(offerService.update(offerTemp), offerDtoTemp);
        return offerDtoTemp;
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
        OfferDto offerDtoTemp = new OfferDto();
        modelMapper.map(offerService.findById(id), offerDtoTemp);
        return offerDtoTemp;
    }

    /**
     * Method that finds all objects.
     *
     * @return founded objects
     */
    @GetMapping
    public List<OfferDto> getAll() {
        List<Offer> offersTemp = offerService.findAll();
        List<OfferDto> offersDtoTemp = new ArrayList<>();
        toOfferDtoList(offersTemp, offersDtoTemp);
        return offersDtoTemp;
    }

    /**
     * Method that changes Category to Offer.
     *
     * @param offerId      Offer Long
     * @param categoryName Category to change
     * @return updated Offer
     */
    @PutMapping(path = "/{offerId}/categories/{categoryName}")
    public OfferDto changeCategory(@PathVariable("offerId") UUID offerId,
                        @PathVariable("categoryName") String categoryName) {
        OfferDto offerDtoTemp = new OfferDto();
        modelMapper.map(offerService.changeCategory(offerId, categoryName), offerDtoTemp);
        return offerDtoTemp;
    }

    @GetMapping(path = "/filter")
    public List<OfferDto> getAllByFilter(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "priceFrom", required = false) String priceFrom,
            @RequestParam(value = "priceTo", required = false) String priceTo) {
        List<Offer> offersTemp = offerService.findAllByFilter(category, priceFrom, priceTo);
        List<OfferDto> offersDtoTemp = new ArrayList<>();
        toOfferDtoList(offersTemp, offersDtoTemp);
        return offersDtoTemp;
    }

    private void toOfferDtoList(List<Offer> offersTemp, List<OfferDto> offersDtoTemp){
        for (Offer offer : offersTemp) {
            OfferDto offerDtoTemp = new OfferDto();
            modelMapper.map(offer,offerDtoTemp);
            offersDtoTemp.add(offerDtoTemp);
        }
    }
}
