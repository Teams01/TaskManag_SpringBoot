package com.coderdot.services.Implemtations;

import com.coderdot.dto.CommentDTO;
import com.coderdot.entities.Comment;
import com.coderdot.entities.Customer;
import com.coderdot.entities.Task;
import com.coderdot.repository.CustomerRepository;
import com.coderdot.repository.CommentRepository;
import com.coderdot.repository.TaskRepository;
import com.coderdot.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class CommentImp implements CommentService {
    @Autowired
    CustomerRepository userRepository;
    @Autowired
    TaskRepository taskRepository;

    @Autowired
    CommentRepository commentRepository;
    @Override
    public Long addComment(CommentDTO commentDTO) {
       Customer user = userRepository.findById(commentDTO.getAuthor()).orElseThrow(()->new IllegalArgumentException("User not found"));
       Task task = taskRepository.findById(commentDTO.getTask()).orElseThrow(()->new IllegalArgumentException("Task nit found"));
        Comment comment = new Comment();
        comment.setContent(commentDTO.getContent());
        Date date = new Date();
        comment.setCreatedAt(date);
        comment.setAuthor(user);
        comment.setTask(task);
        commentRepository.save(comment);
        return comment.getId();
    }
    @Override
    public List<Comment> getAllComments() {

        return commentRepository.findAll();
    }
    @Override
    public Comment getCommentById(Long id) {

        return commentRepository.findById(id).get();
    }
    @Override
    public Long updateComment(Long id, CommentDTO commentDTO) {
        Customer user = userRepository.findById(commentDTO.getAuthor()).orElseThrow(()->new IllegalArgumentException("User not found"));
        Task task = taskRepository.findById(commentDTO.getTask()).orElseThrow(()->new IllegalArgumentException("Task nit found"));
        Comment commentToUpdate = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException(" Comment not found "));
        commentToUpdate.setContent(commentDTO.getContent());
        commentToUpdate.setAuthor(user);
        commentToUpdate.setTask(task);
        commentRepository.save(commentToUpdate);
        return commentToUpdate.getId();
    }
    @Override
    public void deleteComment(Long id) {

        commentRepository.deleteById(id);
    }
    @Override
    public Long updateCommentContent(Long id, CommentDTO commentDTO) {
        Comment commentToUpdate = commentRepository.findById(id).orElseThrow(()->new IllegalArgumentException(" Comment not found "));
        commentToUpdate.setContent(commentDTO.getContent());
        commentRepository.save(commentToUpdate);
        return commentToUpdate.getId();
    }


}
