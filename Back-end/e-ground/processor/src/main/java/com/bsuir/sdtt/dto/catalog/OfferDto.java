package com.bsuir.sdtt.dto.catalog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Class of Offer Data Transfer Object. Used to transfer data between application subsystems.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
public class OfferDto {
    private UUID id;

    /**
     * Field of Offer name.
     */
    @NotNull
    private String name;

    /**
     * Field of Offer category.
     */
    @NotNull
    private String category;

    @NotNull
    private String description;

    /**
     * Field of Offer price.
     */
    @NotNull
    @Min(0)
    private double price;

    public OfferDto() {

    }
}
