package com.bsuir.sdtt.client;

import com.bsuir.sdtt.dto.catalog.CategoryDto;
import com.bsuir.sdtt.dto.catalog.OfferDto;
import com.bsuir.sdtt.util.CatalogClientProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

/**
 * Class of Catalog Client
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Component
@Slf4j
public class CatalogClient {
    private final RestTemplate restTemplate;

    @Value("${catalog.url}")
    private String baseUrl;

    @Autowired
    public CatalogClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OfferDto save(OfferDto offerDto) {
        log.info("Start method CatalogClient.save: {}", offerDto);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CatalogClientProperty.API_CATALOG_OFFERS);

        log.info("Final URL: {}", finalUrl.toString());

        ResponseEntity<OfferDto> responseEntity = restTemplate
                .postForEntity(finalUrl.toString(), offerDto, OfferDto.class);

        log.info("Offer DTO: {}", responseEntity.getBody());
        return responseEntity.getBody();
    }


    public OfferDto update(OfferDto offerDto) {
        log.info("Start method CatalogClient.update: {}", offerDto);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CatalogClientProperty.API_CATALOG_OFFERS);

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<OfferDto> entity = new HttpEntity<>(offerDto, headers);

        log.info("Final URL: {}", finalUrl.toString());

        ResponseEntity<OfferDto> responseEntity = restTemplate
                .exchange(finalUrl.toString(), HttpMethod.PUT,
                        entity, OfferDto.class);

        log.info("Offer DTO: {}", responseEntity.getBody());

        return responseEntity.getBody();
    }

    public OfferDto getOfferDto(UUID id) {
        log.info("Start method CatalogClient.getOfferDto Id = {}", id);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CatalogClientProperty.API_CATALOG_OFFERS);
        finalUrl.append(id);

        log.info("Final URL: {}", finalUrl.toString());

        ResponseEntity<OfferDto> responseEntity = restTemplate
                .exchange(finalUrl.toString(), HttpMethod.GET,
                        getHttpEntityHeader(), OfferDto.class);

        log.info("Offer DTO: {}", responseEntity.getBody());

        return responseEntity.getBody();
    }

    public List<OfferDto> getOffersDtoByFilter
            (String category, String priceFrom, String priceTo) {
        log.info("Start method CatalogClient.getOffersDtoByFilter" +
                        " Category = {} Price From = {} Price To = {}",
                category, priceFrom, priceTo);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CatalogClientProperty.API_CATALOG_OFFERS_FILTER);
        finalUrl.append("?");

        if (category != null) {
            finalUrl.append("category=");
            finalUrl.append(category);
        }

        if (priceFrom != null && category == null) {
            finalUrl.append("priceFrom=");
            finalUrl.append(priceFrom);
        } else if (priceFrom != null && category != null) {
            finalUrl.append("&priceFrom=");
            finalUrl.append(priceFrom);
        }

        if (priceTo != null && priceFrom == null) {
            finalUrl.append("priceTo=");
            finalUrl.append(priceTo);
        } else if (priceTo != null && priceFrom != null) {
            finalUrl.append("&priceTo=");
            finalUrl.append(priceTo);
        }

        return getResponseEntity(finalUrl);
    }

    public List<CategoryDto> getAllCategories() {
        log.info("Start method CatalogClient.getAllCategories");

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(CatalogClientProperty.API_CATALOG_CATEGORIES);
        finalUrl.append("?");

        log.info("Final URL: {}", finalUrl.toString());

        ResponseEntity<CategoryDto[]> responseEntity = restTemplate
                .exchange(finalUrl.toString(), HttpMethod.GET,
                        getHttpEntityHeader(), CategoryDto[].class);

        log.info("Size Categories DTO: {}", Objects
                .requireNonNull(responseEntity.getBody()).length);

        return Arrays.asList(responseEntity.getBody());
    }

    private List<OfferDto> getResponseEntity(StringBuilder finalUrl) {
        log.info("Final URL: {}", finalUrl.toString());

        ResponseEntity<OfferDto[]> responseEntity = restTemplate
                .exchange(finalUrl.toString(), HttpMethod.GET,
                        getHttpEntityHeader(), OfferDto[].class);

        log.info("Size Orders DTO: {}", Objects
                .requireNonNull(responseEntity.getBody()).length);

        return Arrays.asList(responseEntity.getBody());
    }

    private HttpEntity<String> getHttpEntityHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        return entity;
    }
}
