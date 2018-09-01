package pl.toby.comment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.toby.comment.PostComment;
import pl.toby.comment.PostCommentRepository;
import pl.toby.post.Post;
import pl.toby.post.service.PostServiceImpl;
import pl.toby.user.User;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PostCommentServiceImpl implements PostCommentService {

    private PostCommentRepository postCommentRepository;
    private PostServiceImpl postService;

    @Autowired
    public PostCommentServiceImpl(PostCommentRepository postCommentRepository, PostServiceImpl postService) {
        this.postCommentRepository = postCommentRepository;
        this.postService = postService;
    }


    @Override
    public List<PostComment> findAll() {
        return postCommentRepository.findAllByOrderByDateDesc();
    }

    @Override
    public List<PostComment> findByPostId(UUID postId) {
        System.out.println(postId.toString());
        return postCommentRepository.findAllByOrderByDateDesc()
                .stream()
                .filter(c -> c.getPost().getId().equals(postId))
                .collect(Collectors.toList());
    }


    @Override
    public PostComment addComment(UUID postId, User user, PostComment postComment) {
        postCommentRepository.save(postComment);
        Post post = postService.findById(postId);

        postComment.setDate(LocalDate.now());
        post.increaseCommentsCount();

        postComment.setPost(post);
        postComment.setUser(user);

        postCommentRepository.save(postComment);
        return postComment;
    }

    @Override
    public PostComment editComment(PostComment postComment) {
        PostComment comment = postCommentRepository.findById(postComment.getId());

        if(postComment.getContent() != null)
            comment.setContent(postComment.getContent());

        postCommentRepository.save(comment);
        return comment;
    }

    @Override
    public String removeComment(PostComment postComment) {
        PostComment comment = postCommentRepository.findById(postComment.getId());
        comment.getPost().decreaseCommentsCount();
        postCommentRepository.delete(postComment.getId());

        if(postCommentRepository.findById(postComment.getId()) == null) {
            return "Comment has been removed successfully";
        }

        return "Something went wrong with removing comment.";
    }
}
