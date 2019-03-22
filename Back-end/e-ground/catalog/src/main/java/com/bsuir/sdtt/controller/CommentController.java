package com.bsuir.sdtt.controller;

import com.bsuir.sdtt.dto.CommentDto;
import com.bsuir.sdtt.entity.Comment;
import com.bsuir.sdtt.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Stsiapan Balashenka
 * @version 1.0
 */
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "api/v1/catalog/comments")
public class CommentController {
    /**
     * Field of Comment Service.
     */
    private final CommentService commentService;

    /**
     * Field of Model Mapper converter.
     */
    private final ModelMapper modelMapper;

    @Autowired
    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(path = "/{offerId}")
    public List<CommentDto> getAllCommentsByOfferId(@PathVariable("offerId") UUID offerId){
        List<Comment> commentsTemp = commentService.getAllCommentsByOfferId(offerId);
        List<CommentDto> commentsDtoTemp = new ArrayList<>();
        toCommentDtoList(commentsTemp, commentsDtoTemp);
        return commentsDtoTemp;
    }

    private void toCommentDtoList(List<Comment> commentsTemp, List<CommentDto> commentsDtoTemp){
        for (Comment comment : commentsTemp) {
            CommentDto commentDtoTemp = new CommentDto();
            modelMapper.map(comment,commentDtoTemp);
            commentsDtoTemp.add(commentDtoTemp);
        }
    }
}
