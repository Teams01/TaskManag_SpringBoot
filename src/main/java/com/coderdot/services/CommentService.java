package com.coderdot.services;

import com.coderdot.dto.CommentDTO;
import com.coderdot.entities.Comment;

import java.util.List;

public interface CommentService {
    public Long addComment(CommentDTO commentDTO);
    public List<Comment> getAllComments();
    public Comment getCommentById(Long id);
    public Long updateComment(Long id ,CommentDTO commentDTO);
    public void deleteComment(Long id);

    Long updateCommentContent(Long id, CommentDTO commentDTO);
}
