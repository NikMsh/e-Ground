package com.bsuir.sdtt.controller;

import com.bsuir.sdtt.dto.ConversationDTO;
import com.bsuir.sdtt.mapper.ConversationMapper;
import com.bsuir.sdtt.service.ConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/customer-management/conversations")
public class ConversationController {

    private ConversationService conversationService;

    private ConversationMapper conversationMapper;

    @Autowired
    public ConversationController(ConversationService conversationService,
                                  ConversationMapper conversationMapper) {
        this.conversationService = conversationService;
        this.conversationMapper = conversationMapper;
    }


    @GetMapping("/getConversationInfoByUsersIds")
    public ConversationDTO getConversationInfo(@RequestParam(name = "yourId") UUID yourId,
                                               @RequestParam(name = "otherId") UUID otherId) {
        return conversationMapper.conversationToConversationDTO(
                conversationService.getConversationByUsersIds(yourId, otherId).orElseThrow(
                        () -> new EntityNotFoundException("Conversation not found for users: " + yourId + "and " + otherId)
                ));
    }

    @GetMapping("/{userId}")
    public List<ConversationDTO> getConversationsByUserId(@PathVariable(name = "userId") UUID userId) {
        return conversationMapper
                .conversationDTOtoConversation(
                        conversationService.getUserConversationsByUserId(userId)
                );
    }
}