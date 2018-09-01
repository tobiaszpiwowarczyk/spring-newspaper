package pl.toby.post;

import pl.toby.comment.PostComment;
import pl.toby.core.misc.BaseEntity;
import pl.toby.post.category.PostCategory;
import pl.toby.post.image.PostImage;
import pl.toby.post.rating.PostRating;
import pl.toby.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Post extends BaseEntity {

    private String title;

    @Column(length = 1000000)
    private String description;

    @Column(length = 2147483647)
    private String content;

    private String[] tags;

    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private PostCategory category;

    @OneToOne(cascade = CascadeType.ALL)
    private PostRating rating;

    @OneToOne(cascade = CascadeType.ALL)
    private PostImage image;

    @ManyToOne
    private User user;

    private int commentsCount;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @OrderBy("date DESC")
    private List<PostComment> comments;




    public Post() {
        super();
        this.comments = new ArrayList<>();
    }

    public Post(String title, String description, String content, String[] tags, LocalDate date, PostCategory category, PostRating rating, PostImage image) {
        this();
        this.title = title;
        this.description = description;
        this.content = content;
        this.tags = tags;
        this.date = date;
        this.category = category;
        this.rating = rating;
        this.image = image;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public PostRating getRating() {
        return rating;
    }

    public void setRating(PostRating rating) {
        this.rating = rating;
    }

    public PostCategory getCategory() {
        return category;
    }

    public void setCategory(PostCategory category) {
        this.category = category;
    }

    public PostImage getImage() {
        return image;
    }

    public void setImage(PostImage image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public void increaseCommentsCount() {
        this.commentsCount++;
    }

    public void decreaseCommentsCount() {
        this.commentsCount--;
    }

    public List<PostComment> getComments() {
        return comments;
    }
}
