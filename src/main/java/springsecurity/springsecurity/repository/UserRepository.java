package springsecurity.springsecurity.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import springsecurity.springsecurity.user.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findByUsername(String username);
}
