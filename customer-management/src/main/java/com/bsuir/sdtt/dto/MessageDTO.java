package com.bsuir.sdtt.dto;

import lombok.*;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

    private UUID conversationId;

    private UUID senderId;

    private UUID receiverId;

    private String message;

    private Timestamp creationDate;
}
