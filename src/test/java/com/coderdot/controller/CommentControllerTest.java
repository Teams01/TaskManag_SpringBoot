package com.coderdot.controller;



import com.coderdot.controllers.CommentController;
import com.coderdot.dto.CommentDTO;
import com.coderdot.entities.Comment;
import com.coderdot.services.CommentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CommentControllerTest {

    @Mock
    private CommentService commentService;

    @InjectMocks
    private CommentController commentController;

    private CommentDTO commentDTO;
    private Comment comment;

    @BeforeEach
    void setUp() {
        commentDTO = new CommentDTO();
        commentDTO.setId(1L);
        commentDTO.setContent("Test comment");

        comment = new Comment();
        comment.setId(1L);
        comment.setContent("Test comment");
    }

    @Test
    void testAddComment() {
        when(commentService.addComment(commentDTO)).thenReturn(1L);
        ResponseEntity<Long> response = commentController.addComment(commentDTO);
        assertEquals(1L, response.getBody());
        verify(commentService, times(1)).addComment(commentDTO);
    }

    @Test
    void testGetAllComments() {
        when(commentService.getAllComments()).thenReturn(Arrays.asList(comment));
        ResponseEntity<List<Comment>> response = commentController.getAllComments();
        assertEquals(1, response.getBody().size());
        assertEquals("Test comment", response.getBody().get(0).getContent());
        verify(commentService, times(1)).getAllComments();
    }

    @Test
    void testGetCommentById() {
        when(commentService.getCommentById(1L)).thenReturn(comment);
        ResponseEntity<Comment> response = commentController.getCommentById(1L);
        assertEquals(1L, response.getBody().getId());
        assertEquals("Test comment", response.getBody().getContent());
        verify(commentService, times(1)).getCommentById(1L);
    }

    @Test
    void testUpdateComment() {
        when(commentService.updateComment(1L, commentDTO)).thenReturn(1L);
        ResponseEntity<Long> response = commentController.updateComment(1L, commentDTO);
        assertEquals(1L, response.getBody());
        verify(commentService, times(1)).updateComment(1L, commentDTO);
    }

    @Test
    void testDeleteComment() {
        doNothing().when(commentService).deleteComment(1L);
        ResponseEntity<Void> response = commentController.deleteComment(1L);
        assertEquals(204, response.getStatusCodeValue());
        verify(commentService, times(1)).deleteComment(1L);
    }
}
