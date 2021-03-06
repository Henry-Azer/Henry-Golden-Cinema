package henry.goldencinema.repository;

import henry.goldencinema.entity.review.Rate;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RateRepository extends MongoRepository<Rate, String> {
}
