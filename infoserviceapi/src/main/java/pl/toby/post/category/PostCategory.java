package pl.toby.post.category;


import java.util.Arrays;

public enum PostCategory {

    MUSIC,
    HEALTH,
    SCIENCE,
    WAR,
    POLITICS,
    FOOD;


    public static PostCategory fromValue(String value) {
        for(PostCategory category: values()) {
            if (category.toString().equalsIgnoreCase(value)) {
                return category;
            }
        }

        throw new IllegalArgumentException("Unknown enum type " + value + ". Allowed values: " + Arrays.toString(values()));
    }
}
