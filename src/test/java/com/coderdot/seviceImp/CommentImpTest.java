package com.coderdot.seviceImp;



import com.coderdot.dto.CommentDTO;
import com.coderdot.entities.Comment;
import com.coderdot.entities.Customer;
import com.coderdot.entities.Task;
import com.coderdot.repository.CommentRepository;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.repository.TaskRepository;
import com.coderdot.services.Implemtations.CommentImp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CommentImpTest {

    @Mock
    private CustomerRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentImp commentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /*@Test
    void addComment_ShouldAddCommentAndReturnId() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAuthor(1L);
        commentDTO.setTask(2L);
        commentDTO.setContent("Test content");

        Customer user = new Customer();
        user.setId(1L);

        Task task = new Task();
        task.setId(2L);

        Comment comment = new Comment();
        comment.setId(10L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.findById(2L)).thenReturn(Optional.of(task));
        when(commentRepository.save(any(Comment.class))).thenReturn(comment);

        Long commentId = commentService.addComment(commentDTO);

        assertNotNull(commentId);
        assertEquals(10L, commentId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }*/

    @Test
    void getAllComments_ShouldReturnAllComments() {
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();

        when(commentRepository.findAll()).thenReturn(Arrays.asList(comment1, comment2));

        List<Comment> comments = commentService.getAllComments();

        assertNotNull(comments);
        assertEquals(2, comments.size());
        verify(commentRepository, times(1)).findAll();
    }

    @Test
    void getCommentById_ShouldReturnComment() {
        Comment comment = new Comment();
        comment.setId(1L);

        when(commentRepository.findById(1L)).thenReturn(Optional.of(comment));

        Comment retrievedComment = commentService.getCommentById(1L);

        assertNotNull(retrievedComment);
        assertEquals(1L, retrievedComment.getId());
        verify(commentRepository, times(1)).findById(1L);
    }

    @Test
    void updateComment_ShouldUpdateCommentAndReturnId() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setAuthor(1L);
        commentDTO.setTask(2L);
        commentDTO.setContent("Updated content");

        Customer user = new Customer();
        user.setId(1L);

        Task task = new Task();
        task.setId(2L);

        Comment existingComment = new Comment();
        existingComment.setId(3L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.findById(2L)).thenReturn(Optional.of(task));
        when(commentRepository.findById(3L)).thenReturn(Optional.of(existingComment));

        Long updatedId = commentService.updateComment(3L, commentDTO);

        assertNotNull(updatedId);
        assertEquals(3L, updatedId);
        verify(commentRepository, times(1)).save(existingComment);
    }

    @Test
    void deleteComment_ShouldDeleteComment() {
        Long commentId = 5L;

        doNothing().when(commentRepository).deleteById(commentId);

        commentService.deleteComment(commentId);

        verify(commentRepository, times(1)).deleteById(commentId);
    }

    @Test
    void updateCommentContent_ShouldUpdateCommentContentAndReturnId() {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setContent("Updated content");

        Comment existingComment = new Comment();
        existingComment.setId(4L);

        when(commentRepository.findById(4L)).thenReturn(Optional.of(existingComment));

        Long updatedId = commentService.updateCommentContent(4L, commentDTO);

        assertNotNull(updatedId);
        assertEquals(4L, updatedId);
        assertEquals("Updated content", existingComment.getContent());
        verify(commentRepository, times(1)).save(existingComment);
    }
}
