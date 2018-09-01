package pl.toby.post.image;

import pl.toby.core.misc.BaseEntity;

import javax.persistence.Entity;

@Entity
public class PostImage extends BaseEntity {

    private static final String URL = "https://images.pexels.com/photos/";
    private static final String IMAGE_NAME_PREFIX = "/pexels-photo-";
    private static final String IMAGE_NAME_SUFFIX = ".jpeg?w=%s&h=%s";

    private String thumbnail;
    private String landscape;



    public PostImage() {
        super();
    }

    public PostImage(long photoNumber) {
        this();
        String path = URL + photoNumber + IMAGE_NAME_PREFIX + photoNumber + IMAGE_NAME_SUFFIX;
        this.thumbnail = String.format(path, getWidth(PostImageDimmension.THUMBNAIL), getHeight(PostImageDimmension.THUMBNAIL));
        this.landscape = String.format(path, getWidth(PostImageDimmension.LANDSCAPE), getHeight(PostImageDimmension.LANDSCAPE));
    }



    public String getThumbnail() {
        return thumbnail;
    }
    public String getLandscape() {
        return landscape;
    }


    private int getWidth(PostImageDimmension dimmension) {
        return dimmension.getWidth(dimmension);
    }
    private int getHeight(PostImageDimmension dimmension) {
        return dimmension.getHeight(dimmension);
    }
}
