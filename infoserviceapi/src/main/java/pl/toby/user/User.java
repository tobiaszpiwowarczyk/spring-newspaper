package pl.toby.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.toby.comment.PostComment;
import pl.toby.core.misc.BaseEntity;
import pl.toby.post.Post;
import pl.toby.post.rating.PostRating;
import pl.toby.user.role.UserRole;
import pl.toby.user.sex.UserSex;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class User extends BaseEntity implements UserDetails {

    private static final String AVATAR_PREFIX = "/media/";

    @Column(unique = true)
    private String username;
    private String password;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;
    private UserSex userSex;
    private String avatar;
    private String bgImage;

    @ElementCollection
    private List<String> roles;
    private String role;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Post> posts;

    @ManyToMany(mappedBy = "thumbsUp", cascade = CascadeType.ALL)
    private List<PostRating> likedPostsUp;

    @ManyToMany(mappedBy = "thumbsDown", cascade = CascadeType.ALL)
    private List<PostRating> likedPostsDown;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OrderBy("date DESC")
    private List<PostComment> comments;




    public User() {
        super();
        this.roles = new ArrayList<>();
        this.posts = new ArrayList<>();
        this.likedPostsUp = new ArrayList<>();
        this.likedPostsDown = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public User(String username, String password, String firstName, String lastName, String email, UserSex userSex, UserRole role) {
        this();
        this.username = username;
        this.setPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userSex = userSex;
        this.setRoles(role);
        this.role = role.toString().toLowerCase();
    }





    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }




    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserSex getUserSex() {
        return userSex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String imageFolder, String avatar) {
        this.avatar = AVATAR_PREFIX + imageFolder + "/" + avatar;
    }

    public String getBgImage() {
        return bgImage;
    }

    public void setBgImage(String imageFolder, String bgImage) {
        this.bgImage = AVATAR_PREFIX + imageFolder + "/" + bgImage;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(UserRole role) {
        this.roles = Arrays.asList(role.getRoles());
    }

    public boolean hasRole(UserRole role) {
        return roles.containsAll(Arrays.asList(role.getRoles()));
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public List<PostRating> getLikedPostsUp() {
        return likedPostsUp;
    }

    public List<PostRating> getLikedPostsDown() {
        return likedPostsDown;
    }

    public List<PostComment> getComments() {
        return comments;
    }

}
