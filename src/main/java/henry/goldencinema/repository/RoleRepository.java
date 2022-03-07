package henry.goldencinema.repository;

import henry.goldencinema.entity.user.Role;
import henry.goldencinema.entity.enums.ERole;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {

    Role findRoleByName(ERole name);
}
