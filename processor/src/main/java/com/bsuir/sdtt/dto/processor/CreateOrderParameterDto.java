package com.bsuir.sdtt.dto.processor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * Class of Order Create Parameter.
 * Used to create order from offer.
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@Data
@Builder
@AllArgsConstructor
public class CreateOrderParameterDto {
    private UUID customerId;

    private UUID itemId;

    private int itemCount;
}
