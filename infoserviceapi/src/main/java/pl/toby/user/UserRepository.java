package pl.toby.user;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    List<User> findAll();
    User findById(UUID id);
    User findByUsername(String username);

}
