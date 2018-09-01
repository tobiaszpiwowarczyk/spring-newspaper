package pl.toby.post.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.toby.post.Post;
import pl.toby.post.PostRepository;
import pl.toby.post.category.PostCategory;
import pl.toby.post.rating.PostRating;
import pl.toby.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }



    // ----------------------------------------------------------------------------------------------

    @Override
    public Page<Post> findAll(Pageable pageable) {
        return postRepository.findAllByOrderByDateDesc(pageable);
    }

    // ----------------------------------------------------------------------------------------------

    @Override
    public Post findById(UUID id) {
        return postRepository.findById(id);
    }

    // ----------------------------------------------------------------------------------------------

    @Override
    public List<String> findAllTags() {

        String[] tags = postRepository.getAllTags();
        TreeSet<String> result = new TreeSet<>();

        for(int i = 0; i < tags.length; i++) {
            String[] _tag = tags[i].split(",");

            for(int j = 0; j < _tag.length; j++) {
                result.add(_tag[j]);
            }
        }

        return new ArrayList<>(result);
    }

    // ----------------------------------------------------------------------------------------------

    @Override
    public List<String> findTagsByTagName(String value) {

        Pattern p = Pattern.compile("^"+ Pattern.quote(value));
        List<String> allTags = this.findAllTags();
        List<String> result = new ArrayList<>();

        for(int i = 0; i < allTags.size(); i++) {
            if(p.matcher(allTags.get(i)).find()) {
                result.add(allTags.get(i));
            }
        }

        return result;
    }

    // ----------------------------------------------------------------------------------------------

    @Override
    public Page<Post> findPostsByTagName(Pageable pageable, String value) {

        Pattern p = Pattern.compile("^"+ Pattern.quote(value));
        List<Post> allPosts = this.findAll(pageable).getContent();
        List<Post> result = new ArrayList<>();

        for(int i = 0; i < allPosts.size(); i++) {
            Post post = allPosts.get(i);

            for(int j = 0; j < post.getTags().length; j++) {
                if(p.matcher(post.getTags()[j]).find()) {
                    result.add(post);
                    break;
                }
            }
        }

        return new PageImpl<>(result, pageable, allPosts.size());
    }

    // ----------------------------------------------------------------------------------------------

    @Override
    public Page<Post> findPostsByCategory(Pageable pageable, PostCategory category) {

        List<Post> res = postRepository.findByCategoryOrderByDateDesc(category);

        return new PageImpl<>(res, pageable, res.size());
    }

    // ----------------------------------------------------------------------------------------------

    @Override
    public PostCategory[] findAllPostCategories() {
        return PostCategory.values();
    }

    // ----------------------------------------------------------------------------------------------

    @Override
    public PostRating like(UUID postID, User user, String thumbType) {
        Post post = this.findById(postID);

        switch(thumbType) {
            case "up":

                if(post.getRating().getThumbsDown().contains(user))
                    post.getRating().removeUserDown(user);

                if(!post.getRating().getThumbsUp().contains(user))
                    post.getRating().addUserUp(user);
                else
                    post.getRating().removeUserUp(user);

                break;
            case "down":

                if(post.getRating().getThumbsUp().contains(user))
                    post.getRating().removeUserUp(user);

                if(!post.getRating().getThumbsDown().contains(user))
                    post.getRating().addUserDown(user);
                else
                    post.getRating().removeUserDown(user);

                break;
            default:
                throw new RuntimeException("Nie rozpoznany typ oceny. Dostępne typy to: ['up', 'down']");
        }

        postRepository.save(post);
        return post.getRating();
    }

}
