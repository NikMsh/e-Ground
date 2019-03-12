package com.bsuir.entity;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Class of customer that extends BaseEntity class.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Data
@Entity
@Table(name = "customers")
public class Customer extends BaseEntity {
    /**
     * Field of customer name.
     */
    @Basic(optional = false)
    @NotNull
    private String name;

    /**
     * Field of customer surname.
     */
    @NotNull
    private String surname;

    /**
     * Field of customer email.
     */
    @Email
    @NotNull
    private String email;

    @NotNull
    private String password;

    /**
     * Field of customer age.
     */
    @Min(1)
    @NotNull
    private int age;

    /**
     * Field of customer number
     */
    @NotNull
    @Column(unique = true)
    @Pattern(regexp = "^\\+375(29|33|44)\\d{7}$")
    private String phoneNumber;

    /**
     * Constructor without params that create object without initialization fields.
     */
    public Customer() {
    }
}
