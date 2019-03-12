package com.bsuir.dto.processor;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateOrderParameterDto {
    private UUID customerId;

    private UUID itemId;

    private int itemCount;
}
