package com.bsuir.client;

import com.bsuir.dto.catalog.OfferDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class CatalogClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(CatalogClient.class);

    private static final String API_CATALOG_OFFERS = "/api/v1/catalog/offers/";

    private static final String API_CATALOG_OFFERS_FILTER = "/api/v1/catalog/offers/filter";

    private final RestTemplate restTemplate;

    @Value("${catalog.url}")
    private String baseUrl;

    @Autowired
    public CatalogClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OfferDto save(OfferDto offerDto) {
        LOGGER.info("Start method save CatalogClient.OfferDto: {}", offerDto);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(API_CATALOG_OFFERS);

        LOGGER.info("Final URL: {}", finalUrl.toString());

        ResponseEntity<OfferDto> responseEntity = restTemplate.postForEntity(finalUrl.toString(), offerDto, OfferDto.class);

        LOGGER.info("Offer DTO: {}", responseEntity.getBody());
        return responseEntity.getBody();
    }


    public OfferDto getOfferDto(UUID id) {
        LOGGER.info("Start method CatalogClient.getOfferDto Id = {}", id);

        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(API_CATALOG_OFFERS);
        finalUrl.append(id);

        LOGGER.info("Final URL: {}", finalUrl.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<OfferDto> responseEntity = restTemplate.exchange(finalUrl.toString(), HttpMethod.GET, entity, OfferDto.class);
        LOGGER.info("Offer DTO: {}", responseEntity.getBody());
        return responseEntity.getBody();
    }

    public List<OfferDto> getOffersDto(String category, String priceFrom, String priceTo) {
        LOGGER.info("Start method CatalogClient.getOffersDto Tags = {} Category = {} Price From = {} Price To = {}",
                category, priceFrom, priceTo);

        HttpHeaders headers = new HttpHeaders();
        StringBuilder finalUrl = new StringBuilder(baseUrl);
        finalUrl.append(API_CATALOG_OFFERS_FILTER);
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

        LOGGER.info("Final URL: {}", finalUrl.toString());

        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        ResponseEntity<OfferDto[]> responseEntity = restTemplate.exchange(finalUrl.toString(), HttpMethod.GET, entity, OfferDto[].class);

        LOGGER.info("Size Orders DTO: {}", Objects.requireNonNull(responseEntity.getBody()).length);

        return Arrays.asList(responseEntity.getBody());
    }
}
