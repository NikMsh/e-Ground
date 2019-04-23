package com.bsuir.sdtt.mapper;

import com.bsuir.sdtt.dto.ConversationDTO;
import com.bsuir.sdtt.entity.Conversation;
import com.bsuir.sdtt.repository.CustomerRepository;
import com.bsuir.sdtt.service.MessageService;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import javax.security.auth.login.AccountNotFoundException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ConversationMapper {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MessageService messageService;
    @Autowired
    private MessageMapper messageMapper;

    public ConversationDTO conversationToConversationDTO(Conversation conversation) {
        conversation.getMessages().sort(Collections.reverseOrder());
        return ConversationDTO.builder()
                .id(conversation.getId())
                .firstCustomer(conversation.getFirstAccount())
                .secondCustomer(conversation.getSecondAccount())
                .conversationMessages(messageMapper.messageToMessageDTO(conversation.getMessages()))
                .build();
    }

    public Conversation conversationDTOtoConversation(ConversationDTO conversationDTO) {
        return Conversation.builder()
                .id(conversationDTO.getId())
                .firstAccount(customerRepository.findById(conversationDTO.getFirstCustomer().getId()).orElseThrow(
                        () -> new EntityNotFoundException("Account with UUID" + conversationDTO.getFirstCustomer() + "not found")))
                .secondAccount(customerRepository.findById(conversationDTO.getSecondCustomer().getId()).orElseThrow(
                        () -> new EntityNotFoundException("Account with UUID" + conversationDTO.getSecondCustomer() + "not found")))
                .messages(messageService.getConversationMessagesById(conversationDTO.getId()))
                .build();
    }

    public abstract List<ConversationDTO> conversationDTOtoConversation(Collection<Conversation> conversations);
}
