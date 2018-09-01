package pl.toby.post.image;


public enum PostImageDimmension {

    THUMBNAIL(640, 360),
    LANDSCAPE(1920, 1080);

    private int width;
    private int height;

    PostImageDimmension(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth(PostImageDimmension dimmension) {
        return dimmension.width;
    }

    public int getHeight(PostImageDimmension dimmension) {
        return dimmension.height;
    }
}
