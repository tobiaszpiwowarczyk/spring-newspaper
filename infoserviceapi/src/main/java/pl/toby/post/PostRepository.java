package pl.toby.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import pl.toby.post.category.PostCategory;

import java.util.List;
import java.util.UUID;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, UUID> {

    Page<Post> findAllByOrderByDateDesc(Pageable pageable);

    Post findById(UUID id);

    List<Post> findByCategoryOrderByDateDesc(PostCategory category);

    @Query(value = "select p.tags from Post p")
    String[] getAllTags();


}