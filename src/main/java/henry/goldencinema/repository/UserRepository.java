package henry.goldencinema.repository;

import henry.goldencinema.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findAll();

    User findUserById(String id);

    User findUserByEmail(String email);

}
