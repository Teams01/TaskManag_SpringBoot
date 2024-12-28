package com.coderdot.controllers;


import com.coderdot.dto.CommentDTO;
import com.coderdot.entities.Comment;
import com.coderdot.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
@CrossOrigin(origins = "*")
public class CommentController {

    @Autowired
    private CommentService commentService;

    // Ajouter un commentaire
    @PostMapping("/add")
    public ResponseEntity<Long> addComment(@RequestBody CommentDTO commentDTO) {
        Long commentId = commentService.addComment(commentDTO);
        return ResponseEntity.ok(commentId);
    }

    // Récupérer tous les commentaires
    @GetMapping("/all")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    // Récupérer un commentaire par ID
    @GetMapping("/{id}")
    public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        return ResponseEntity.ok(comment);
    }

    // Mettre à jour un commentaire
    @PutMapping("/update/{id}")
    public ResponseEntity<Long> updateComment(@PathVariable Long id, @RequestBody CommentDTO commentDTO) {
        Long updatedCommentId = commentService.updateComment(id, commentDTO);
        return ResponseEntity.ok(updatedCommentId);
    }

    // Supprimer un commentaire
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
