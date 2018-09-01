package pl.toby.post.rating;

import pl.toby.core.misc.BaseEntity;
import pl.toby.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class PostRating extends BaseEntity {

    @ManyToMany
    private List<User> thumbsUp;

    @ManyToMany
    private List<User> thumbsDown;

    private Double value;



    public PostRating() {
        super();
        this.thumbsUp = new ArrayList<>();
        this.thumbsDown = new ArrayList<>();
        this.setValue(thumbsUp, thumbsDown);
    }



    public List<User> getThumbsUp() {
        return thumbsUp;
    }
    public List<User> getThumbsDown() {
        return thumbsDown;
    }

    public Double getValue() {
        return value;
    }



    private void setValue(List<User> thumbsUp, List<User> thumbsDown) {
        if(thumbsUp.size() == 0 && thumbsDown.size() == 0)
            this.value = 0.0;
        else
            this.value = ((double) thumbsUp.size()) / (((double) thumbsUp.size()) + ((double) thumbsDown.size()));
    }

    public void addUserUp(User user) {
        this.thumbsUp.add(user);
        this.setValue(thumbsUp, thumbsDown);
    }
    public void removeUserUp(User user) {
        this.thumbsUp.remove(user);
        this.setValue(thumbsUp, thumbsDown);
    }

    public void addUserDown(User user) {
        this.thumbsDown.add(user);
        this.setValue(thumbsUp, thumbsDown);
    }
    public void removeUserDown(User user) {
        this.thumbsDown.remove(user);
        this.setValue(thumbsUp, thumbsDown);
    }
}
