package com.bsuir.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.UUID;

/**
 * Class of Category Data Transfer Object. Used to transfer data between application subsystems.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Data
public class CategoryDto {
    @Null
    private UUID id;

    /**
     * Field of Category name
     */
    @NotNull
    private String name;

    public CategoryDto() {

    }
}
