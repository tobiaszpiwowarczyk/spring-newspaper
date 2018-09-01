package pl.toby.core.misc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pl.toby.post.Post;
import pl.toby.post.PostRepository;
import pl.toby.post.category.PostCategory;
import pl.toby.post.image.PostImage;
import pl.toby.post.rating.PostRating;
import pl.toby.user.User;
import pl.toby.user.UserRepository;
import pl.toby.user.role.UserRole;
import pl.toby.user.sex.UserSex;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class DatabaseLoader implements ApplicationRunner {

    private static final int NUM_ROWS = 100;

    private List<User> users;
    private List<Post> posts;

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Autowired
    public DatabaseLoader(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

        users = new ArrayList<User>() {
            {
                add(new User("locked", "locked", "Locked", "Locked", "locked@locked.com", UserSex.MALE, UserRole.LOCKED));
                add(new User("anowak", "haslo123", "Anna", "Nowak", "anowak@gmail.com",UserSex.FEMALE, UserRole.JOURNALIST));
                add(new User("jkrawczyk", "haslo123", "Justyna", "Krawczyk", "jkrawczyk@gmail.com", UserSex.FEMALE, UserRole.JOURNALIST));
                add(new User("akwasniewska", "haslo123", "Aneta", "Kwaśniewska", "akwasniewska@gmail.com", UserSex.FEMALE, UserRole.JOURNALIST));
                add(new User("jdoe", "haslo123", "John", "Doe", "jdoe@gmail.com", UserSex.MALE, UserRole.JOURNALIST));
                add(new User("jkowalski", "haslo123", "Jan", "Kowalski", "jkowalski@gmail.com", UserSex.MALE, UserRole.JOURNALIST));
                add(new User("adobrzynski", "haslo123", "Andrzej", "Dobrzyński", "adobrzynski@gmail.com", UserSex.MALE, UserRole.JOURNALIST));
                add(new User("mkapusta", "haslo123", "Marcin", "Kapusta", "mkapusta@gmail.com", UserSex.MALE, UserRole.USER));
                add(new User("akowalewska", "haslo123", "Aneta", "Kowalewska", "akowalewska@gmail.com", UserSex.FEMALE, UserRole.USER));
                add(new User("akownacki", "haslo123", "Adam", "Kownacki", "akownacki@gmail.com", UserSex.MALE, UserRole.ADMIN));
            }
        };
        posts = new ArrayList<>();

        String[] titlesPrefixes = {
                "NIESAMOWITE: %s",
                "SKANDAL: %s",
                "Odkryto %s",
                "%s został aresztowany",
                "PILNE: %s",
                "%s potrzebował pieniędzy na leczenie",
                "Katastrofa nuklearna w %s już niebawem"
        };

        String[] titleHeroes = {
                "Nową galaktykę",
                "Jan Kowalski",
                "Radom",
                "George Soros",
                "Jakieś tam wiadomości..."
        };

        String[] descriptions = {
                "Lorem ipsum dolor sit amet neque. Integer pharetra leo. Sed egestas volutpat. Nam sed viverra arcu, eget urna. Cras vitae lectus orci, blandit libero. Cras enim dolor in nibh purus, vulputate at, aliquet lorem. Fusce gravida, erat eu ipsum ac dolor. Maecenas nec risus. Morbi nisl felis a bibendum sapien vitae odio eget sapien magna ornare dapibus. Aenean pede eget leo. Aenean commodo tincidunt tellus dolor massa vel quam. Cum.",
                "Praesent justo. Aenean urna quam, ultrices bibendum, tellus. Vestibulum ante eget risus. Sed eget leo mollis ac, laoreet fermentum. Morbi sed nunc. Nunc leo. Integer faucibus orci luctus et netus et magnis dis parturient montes, nascetur ridiculus mus. Nunc in velit non nulla. Pellentesque fermentum eget, elementum eu, ullamcorper eleifend non, leo. Nullam accumsan. In lacus et magnis dis parturient montes, nascetur ridiculus mus. Donec non orci. Ut lorem. Maecenas.",
                "Curabitur sed libero. Class aptent taciti sociosqu ad litora torquent per inceptos hymenaeos. Quisque augue. Aenean pellentesque dui, non sem. Etiam ornare varius, quam tristique bibendum quis, porttitor risus. Morbi sit amet, accumsan faucibus. Sed malesuada fames ac massa id wisi nunc, tempus nunc. Vestibulum vel blandit vel, ornare varius, nisl nulla vitae nunc ac lectus. Praesent tortor vehicula tortor metus eros malesuada eros, rhoncus libero odio a ligula. Lorem.",
                "Vivamus justo. Vivamus euismod. Donec vel laoreet viverra arcu, in elit. Curabitur adipiscing urna. Nunc justo. Integer tristique, mauris dui ut sapien. Maecenas in turpis luctus augue quis nibh placerat porttitor. Maecenas semper risus. Nulla facilisi. Mauris tortor. Praesent tortor venenatis nulla. Duis neque magna ornare dapibus. Aenean nonummy sed, suscipit ultricies, velit et netus et turpis libero, egestas aliquam, wisi magna, tincidunt in, libero. Sed feugiat venenatis, nunc interdum.",
                "Class aptent taciti sociosqu ad litora torquent per inceptos hymenaeos. Fusce ullamcorper. Nam risus tortor, dictum faucibus sem ullamcorper ut, sagittis nec, imperdiet nec, mattis magna. Fusce mollis, purus non eros. Sed dolor. Nullam et orci luctus aliquam, nulla id rutrum ac, felis. Nullam aliquet. Donec faucibus, erat sem luctus et lacus vestibulum ligula. Sed eros. Vestibulum ante pretium wisi. Aenean venenatis in, gravida diam. Fusce nonummy auctor mattis. Pellentesque."
        };

        String[][] tags = {
                { "lorem", "ipsum", "dolor", "sit" },
                { "praesent", "justo", "aenan", "urna" },
                { "curabitur", "sed", "libero", "class" },
                { "vivamus", "justo", "euimod", "donec" },
                { "class", "aptent", "taciti", "sociosqu" }
        };


        PostCategory[] categories = PostCategory.values();

        long[] photoNumbers = {420745, 374845, 397219, 443446, 392018};


        for(int i = 0; i < NUM_ROWS; i++) {
            int randomUser = getRandomNumber(users.size());
            User selectedUser = users.get(randomUser);

            selectedUser.setAvatar("default-staff", "default-avatar.png");
            selectedUser.setBgImage("default-staff", "default-bg-image.png");

            if(selectedUser.hasRole(UserRole.JOURNALIST)) {
                int randomPrefixes = getRandomNumber(titlesPrefixes.length);
                int randomHeroes = getRandomNumber(titleHeroes.length);
                String title = String.format(titlesPrefixes[randomPrefixes], titleHeroes[randomHeroes]);

                int randomDescription = getRandomNumber(descriptions.length);
                String description = descriptions[randomDescription];
                String[] selectedTags = tags[randomDescription];

                int randomContent = getRandomNumber(7) + 1;
                String content = "";

                for(int j = 1; j <= randomContent; j++) {
                    int randomParagraphs = getRandomNumber(3) + 1;
                    content += "<h2>Header "+ j +"</h2>";

                    for(int k = 0; k < randomParagraphs; k++) {
                        String tempDesc = descriptions[randomDescription];
                        content += "<p>"+ tempDesc +"</p>";
                        randomDescription = getRandomNumber(descriptions.length);
                    }
                }

                //generate random date
                long minDay = LocalDate.of(1970, 1, 1).toEpochDay();
                long maxDay = LocalDate.of(2015, 12, 31).toEpochDay();
                long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
                LocalDate postDate = LocalDate.ofEpochDay(randomDay);

                int randomCategory = getRandomNumber(categories.length);
                PostCategory category = categories[randomCategory];

                int randomImage = getRandomNumber(photoNumbers.length);
                PostImage postImage = new PostImage(photoNumbers[randomImage]);

                Post post = new Post(title, description, content, selectedTags, postDate, category, new PostRating(), postImage);
                post.setUser(selectedUser);
                posts.add(post);
            }

        }

        userRepository.save(this.users);
        postRepository.save(this.posts);
    }


    private int getRandomNumber(int scope) {
        return (int) Math.floor(Math.random() * scope);
    }

}
