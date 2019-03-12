package com.bsuir.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * Class of offer that extends BaseEntity class.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Data
@Entity
@Table(name = "offers")
public class Offer extends BaseEntity {
    /**
     * Necessarily field of Offer name.
     */
    @Basic(optional = false)
    private String name;

    /**
     * Field of Offer category.
     */
    @ManyToOne(fetch = FetchType.LAZY)
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
