package com.bsuir.sdtt.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Builder
@NoArgsConstructor
@Table(name = "messages")
public class Message extends BaseEntity {

    private UUID senderId;

    private UUID receiverId;

    private String message;

    private LocalDateTime date;
}
