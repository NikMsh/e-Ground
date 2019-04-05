package com.bsuir.sdtt.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

/**
 * Class of customer that extends BaseEntity class.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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


    @OneToMany
    private List<Message> messages;


    /**
     * Method that set values except the password from another customer
     * @param customer
     */
    public void update(Customer customer) {
        name = customer.name;
        surname = customer.surname;
        email = customer.email;
        age = customer.age;
        phoneNumber = customer.phoneNumber;
    }
}
