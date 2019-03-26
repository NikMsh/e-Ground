package com.bsuir.sdtt.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@AllArgsConstructor
public class CategoryDto {
    private UUID id;

    /**
     * Field of Category name
     */
    @NotNull
    private String name;

    public CategoryDto() {

    }
}
