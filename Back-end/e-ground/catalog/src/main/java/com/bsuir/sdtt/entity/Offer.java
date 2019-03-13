package com.bsuir.sdtt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * Class of offer that extends BaseEntity class.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {
    /**
     * Necessarily field of Offer name.
     */
    @Basic(optional = false)
    private String name;

    /**
     * Field of seller ID.
     */
    @NotNull
    private UUID sellerId;

    @NotNull
    private String description;

    /**
     * Field of Offer category.
     */
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @NotNull
    private Category category;

    /**
     * Field of Offer price.
     */
    @NotNull
    @Basic(optional = false)
    private double price;

    /**
     * Constructor without params that create object without initialization fields.
     */
    public Offer() {
    }

}
