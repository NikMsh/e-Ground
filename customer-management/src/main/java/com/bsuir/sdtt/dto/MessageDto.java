package com.bsuir.sdtt.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
public class MessageDto {

    private UUID id;

    private UUID senderId;

    private UUID receiverId;

    private String message;

    private LocalDateTime date;
}
