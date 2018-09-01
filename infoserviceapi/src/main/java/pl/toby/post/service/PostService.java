package pl.toby.post.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import pl.toby.post.Post;
import pl.toby.post.category.PostCategory;
import pl.toby.post.rating.PostRating;
import pl.toby.user.User;

import java.util.List;
import java.util.UUID;

public interface PostService {

    Page<Post> findAll(@PageableDefault Pageable pageable);
    Post findById(UUID id);

    List<String> findAllTags();
    List<String> findTagsByTagName(String value);

    Page<Post> findPostsByTagName(@PageableDefault Pageable pageable, String value);
    Page<Post> findPostsByCategory(@PageableDefault Pageable pageable, PostCategory category);

    PostCategory[] findAllPostCategories();


    PostRating like(UUID postID, User user, String thumbType);
}
