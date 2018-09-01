package pl.toby.comment.service;


import pl.toby.comment.PostComment;
import pl.toby.user.User;

import java.util.List;
import java.util.UUID;

public interface PostCommentService {

    List<PostComment> findAll();
    List<PostComment> findByPostId(UUID postId);

    PostComment addComment(UUID postId, User user, PostComment postComment);
    PostComment editComment(PostComment postComment);
    String removeComment(PostComment postComment);

}
