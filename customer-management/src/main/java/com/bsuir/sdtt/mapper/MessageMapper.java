package com.bsuir.sdtt.mapper;

import com.bsuir.sdtt.dto.MessageDTO;
import com.bsuir.sdtt.entity.Message;
import com.bsuir.sdtt.repository.CustomerRepository;
import com.bsuir.sdtt.service.ConversationService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class MessageMapper {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ConversationService conversationService;

    public Message messageDTOtoMessage(MessageDTO messageDTO) {
        Message m = new Message();
        return Message.builder()
                .id(UUID.randomUUID())
                .body(messageDTO.getMessage())
                .receiver(customerRepository.findById(
                        messageDTO.getReceiverId()).orElseThrow(
                        () -> new EntityNotFoundException("Account with UUID: " + messageDTO.getReceiverId() + " not found")))
                .sender(customerRepository.findById(
                        messageDTO.getSenderId()).orElseThrow(
                        () -> new EntityNotFoundException("Account with UUID: " + messageDTO.getReceiverId() + " not found")))
                .conversation(conversationService.findConversationById(
                        messageDTO.getConversationId()).orElseThrow(
                        () -> new EntityNotFoundException("Conversation with UUID: " + messageDTO.getReceiverId() + " not found")))
                .creationDate(messageDTO.getCreationDate())
                .build();
    }

    public MessageDTO messageToMessageDTO(Message message) {
        return MessageDTO.builder()
                .conversationId(message.getConversation().getId())
                .creationDate(message.getCreationDate())
                .message(message.getBody())
                .receiverId(message.getReceiver().getId())
                .senderId(message.getSender().getId())
                .build();
    }

    public abstract List<MessageDTO> messageToMessageDTO(Collection<Message> messages);
}