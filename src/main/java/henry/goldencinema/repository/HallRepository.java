package henry.goldencinema.repository;

import henry.goldencinema.entity.cinema.Hall;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HallRepository extends MongoRepository<Hall, String> {

    List<Hall> findAll();

    Hall findHallById(String id);

    Hall findHallByName(String name);

}
